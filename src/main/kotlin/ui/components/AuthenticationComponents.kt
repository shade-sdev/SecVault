package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.Font
import ui.theme.PasswordColors

@Composable
fun LoginLeftContent() {
    Image(
        painter = painterResource("assets/img.png"),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .drawWithContent {
                drawContent()
                drawRect(
                    color = PasswordColors.tertiary.copy(alpha = 0.85f),
                    blendMode = BlendMode.SrcAtop
                )
            }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Setting button",
                modifier = Modifier.size(30.dp),
                tint = Color.White
            )
            Text(
                text = "SecVault",
                color = Color.White,
                fontFamily = Font.RussoOne,
                fontWeight = FontWeight.Normal,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = "Secure Password Manager",
            color = Color.White,
            fontFamily = Font.RussoOne,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
        )
    }
}