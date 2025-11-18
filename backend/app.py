from flask import Flask, jsonify, request
from influxdb_client import InfluxDBClient, Point, WritePrecision
import ssl
import certifi
import numpy as np
ssl_context = ssl.create_default_context(cafile=certifi.where())

app = Flask(__name__)

# InfluxDB connection
token = "xSzi0lXzFqy0hBllqtXgGCSon5Wl7Z3-D9d6YW8noNWn8Zn76-M3cJRD8jBNQymlPnbQFnfmVBORNqT6oyJJ4Q=="
org = "Project"
bucket = "neuroband"
url = "https://us-east-1-1.aws.cloud2.influxdata.com"

client = InfluxDBClient(url=url, token=token, org=org, ssl_ca_cert = certifi.where())
write_api = client.write_api()
query_api = client.query_api()

@app.route("/api/write", methods=["POST"])
def write_data():
    data = request.get_json()
    point = Point("sensor_data").field("value", data["value"])
    write_api.write(bucket=bucket, org=org, record=point)
    return jsonify({"message": "Data written successfully!"})

def ir_to_bpm(ir_values, sampling_rate=100):
    ir_values = np.array(ir_values)
    peaks = (np.diff(np.sign(np.diff(ir_values))) < 0).nonzero()[0] + 1

    if len(peaks) < 2:
        return None

    peak_intervals = np.diff(peaks) / sampling_rate
    bpm = 60 / np.mean(peak_intervals)
    return round(bpm, 2)

@app.route("/api/test-bpm", methods=["POST"])
def test_bpm():
    """
    Accepts JSON payload:
    { "ir_values": [list of IR readings], "sampling_rate": 100 }
    """
    data = request.get_json()
    ir_values = data.get("ir_values", [])
    sampling_rate = data.get("sampling_rate", 100)

    bpm = ir_to_bpm(ir_values, sampling_rate)
    return jsonify({"bpm": bpm})

@app.route("/api/query", methods=["GET"])
def query_data():
    query = f'''
        from(bucket: "{bucket}")
        |> range(start: -1h)
        |> filter(fn: (r) => r["_measurement"] == "neuroband_data")
        |> pivot(rowKey:["_time"], columnKey: ["_field"], valueColumn: "_value")
        |> keep(columns: ["_time", "heart_rate", "skin_temp", "acc_x", "acc_y", "acc_z"])
    '''
    
    tables = query_api.query(query, org=org)
    results = []
    
    ir_values = []
    times = []
    for table in tables:
        for record in table.records:
            values = record.values  # access record data dictionary
            results.append({
                "time": values["_time"],
                "temp": values.get("skin_temp"),
                "acc_X": values.get("acc_x"),
                "acc_Y": values.get("acc_y"),
                "acc_Z": values.get("acc_z"),
            })
    # Convert to BPM
    bpm = ir_to_bpm(ir_values) if ir_values else None

    for r in results:
        r['heart_rate'] = bpm
    print("Record values:", results)

    return jsonify(results)




if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
