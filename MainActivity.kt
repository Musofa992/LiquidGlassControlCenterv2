package id.musofa.liquidglass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.SignalCellularAlt
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.backdrop.layerBackdrop
import com.kyant.backdrop.rememberLayerBackdrop

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LiquidGlassDemo() }
    }
}

private val Ink = Color(0xFF10131D)
private val GlassWhite = Color.White.copy(alpha = 0.19f)
private val AccentBlue = Color(0xFF5DA9FF)

@Composable
private fun LiquidGlassDemo() {
    var wifi by remember { mutableStateOf(true) }
    var bluetooth by remember { mutableStateOf(true) }
    var mobile by remember { mutableStateOf(true) }
    var airplane by remember { mutableStateOf(false) }
    var torch by remember { mutableStateOf(false) }
    var brightness by remember { mutableFloatStateOf(0.72f) }
    var volume by remember { mutableFloatStateOf(0.55f) }
    var expanded by remember { mutableStateOf(true) }

    val backdrop = rememberLayerBackdrop {
        drawRect(
            Brush.linearGradient(
                listOf(Color(0xFF18233D), Color(0xFF384E70), Color(0xFF111827))
            )
        )
        drawContent()
    }

    Box(Modifier.fillMaxSize().background(Ink)) {
        // The background layer is the source sampled by Kyant0 Backdrop.
        Box(
            Modifier
                .fillMaxSize()
                .layerBackdrop(backdrop)
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF18233D), Color(0xFF405B7C), Color(0xFF111827))
                    )
                )
        ) {
            Box(Modifier.fillMaxSize().padding(24.dp)) {
                Text(
                    "LIQUID GLASS",
                    color = Color.White.copy(alpha = 0.25f),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopStart)
                )
                Box(
                    Modifier
                        .size(190.dp)
                        .align(Alignment.CenterStart)
                        .offset(x = 150.dp, y = (-110).dp)
                        .clip(CircleShape)
                        .background(Color(0xFF80B9FF).copy(alpha = 0.35f))
                )
                Box(
                    Modifier
                        .size(220.dp)
                        .align(Alignment.BottomStart)
                        .offset(x = (-80).dp, y = 40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFB5A0FF).copy(alpha = 0.22f))
                )
            }
        }

        AnimatedVisibility(
            visible = expanded,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .padding(top = 22.dp, bottom = 18.dp)
                    .drawBackdrop(
                        backdrop = backdrop,
                        shape = { RoundedCornerShape(34.dp) },
                        effects = {
                            vibrancy()
                            blur(18.dp.toPx())
                            lens(14.dp.toPx(), 30.dp.toPx())
                        },
                        onDrawSurface = {
                            drawRect(Color.White.copy(alpha = 0.12f))
                        }
                    )
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Gesture handle: swipe down/up here to hide/show the prototype panel.
                Box(
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(42.dp)
                        .height(5.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.65f))
                        .pointerInput(Unit) {
                            detectVerticalDragGestures(
                                onVerticalDrag = { _, dragAmount ->
                                    if (dragAmount > 3f) expanded = false
                                }
                            )
                        }
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Control Center", color = Color.White, fontSize = 23.sp, fontWeight = FontWeight.SemiBold)
                        Text("Liquid Glass • Preview", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
                    }
                    Text("18:21", color = Color.White.copy(alpha = 0.9f), fontSize = 14.sp)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GlassTile("Wi-Fi", Icons.Default.Wifi, wifi, Modifier.weight(1f)) { wifi = !wifi }
                    GlassTile("Bluetooth", Icons.Default.Bluetooth, bluetooth, Modifier.weight(1f)) { bluetooth = !bluetooth }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GlassTile("Mobile", Icons.Default.SignalCellularAlt, mobile, Modifier.weight(1f)) { mobile = !mobile }
                    GlassTile("Airplane", Icons.Default.FlashlightOn, airplane, Modifier.weight(1f)) { airplane = !airplane }
                    GlassTile("Torch", Icons.Default.FlashlightOn, torch, Modifier.weight(1f)) { torch = !torch }
                }

                GlassSlider("Brightness", brightness, { brightness = it }, "☼")
                GlassSlider("Volume", volume, { volume = it }, "♫")

                Row(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(22.dp))
                        .background(GlassWhite)
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.MusicNote, contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Text("Now Playing", color = Color.White, fontWeight = FontWeight.SemiBold)
                        Text("Media controls prototype", color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp)
                    }
                    Text("Ⅱ   ▶", color = Color.White, fontSize = 18.sp)
                }
                Text(
                    "Prototype UI — tombol saat ini hanya mengubah tampilan.",
                    color = Color.White.copy(alpha = 0.58f),
                    fontSize = 10.sp
                )
            }
        }

        if (!expanded) {
            Box(
                Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
                    .width(54.dp)
                    .height(7.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { _, dragAmount ->
                                if (dragAmount < -2f) expanded = true
                            }
                        )
                    }
            )
        }
    }
}

@Composable
private fun GlassTile(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    active: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val tileColor = if (active) Color(0xFF398BEA).copy(alpha = 0.82f) else GlassWhite
    Column(
        modifier
            .height(86.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(tileColor)
            .padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(30.dp)) {
            Icon(icon, contentDescription = title, tint = Color.White)
        }
        Text(title, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun GlassSlider(
    title: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    symbol: String
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(GlassWhite)
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(symbol, color = Color.White, fontSize = 19.sp)
            Spacer(Modifier.width(8.dp))
            Text(title, color = Color.White, fontWeight = FontWeight.Medium)
            Spacer(Modifier.weight(1f))
            Text("${(value * 100).toInt()}%", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.25f)
            )
        )
    }
}
