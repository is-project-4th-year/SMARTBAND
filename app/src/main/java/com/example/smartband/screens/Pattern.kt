package com.example.smartband.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt
import kotlin.random.Random
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt



@Composable
fun WhatsAppStyleDoodleBackground(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFFEFF3F6),
    iconTint: Color = Color(0xFFCBD5E1),
    densityPer10kPx: Int = 14,
    seed: Int = 20250905
) {
    var intSize by remember { mutableStateOf(IntSize.Zero) }


    val iconSet = remember {
        listOf(
            Icons.Outlined.Message,
            Icons.Outlined.Chat,
            Icons.Outlined.Phone,
            Icons.Outlined.PhotoCamera,
            Icons.Outlined.FavoriteBorder,
            Icons.Outlined.Star,
            Icons.Outlined.Send,
            Icons.Outlined.AttachFile,
            Icons.Outlined.TagFaces,
            Icons.Outlined.LocationOn,
            Icons.Outlined.Mic,
        )
    }

//    val doodles: List<Doodle> by remember(intSize) {
//        mutableStateOf(
//            if (intSize == IntSize.Zero) emptyList() else generateDoodles(
//                intSize = intSize,
//                densityPer10kPx = densityPer10kPx,
//                seed = seed
//            )
//        )
//    }


    data class Doodle(
        val icon: ImageVector,
        val xPx: Float,
        val yPx: Float,
        val sizeDp: Float,
        val rotationDeg: Float,
        val alpha: Float
    )


    fun generateDoodles(
        intSize: IntSize,
        icons: List<ImageVector>,
        densityPer10kPx: Int,
        seed: Int
    ): List<Doodle> {
        val area10k = (intSize.width * intSize.height) / 10_000f
        val count = (area10k * densityPer10kPx).roundToInt().coerceAtLeast(24)


        val rand = Random(seed)
        val cols = 10
        val rows = (count / cols).coerceAtLeast(6)
        val cellW = intSize.width.toFloat() / cols
        val cellH = intSize.height.toFloat() / rows


        return buildList(count) {
            var placed = 0
            outer@ for (r in 0 until rows) {
                for (c in 0 until cols) {
                    if (placed >= count) break@outer
                    val icon = icons[placed % icons.size]


                    val jitterX = (rand.nextFloat() - 0.5f) * 0.6f * cellW
                    val jitterY = (rand.nextFloat() - 0.5f) * 0.6f * cellH


                    val x = c * cellW + cellW / 2f + jitterX
                    val y = r * cellH + cellH / 2f + jitterY


                    val sizeDp = when (rand.nextInt(3)) {
                        0 -> 12f
                        1 -> 16f
                        else -> 20f
                    }
                    val rotation = (rand.nextFloat() - 0.5f) * 30f
                    val alpha = 0.45f + rand.nextFloat() * 0.25f


                    add(
                        Doodle(
                            icon = icon,
                            xPx = x.coerceIn(0f, intSize.width - 1f),
                            yPx = y.coerceIn(0f, intSize.height - 1f),
                            sizeDp = sizeDp,
                            rotationDeg = rotation,
                            alpha = alpha
                        )
                    )
                    placed++
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TiledPatternPreview() {
    WhatsAppStyleDoodleBackground(
        backgroundColor = Color(0xFFEFF3F6),
        iconTint = Color(0xFFCBD5E1),
        densityPer10kPx = 16,  // controls how crowded the icons are
        seed = 42              // change this to reshuffle the doodle layout
    )

}
