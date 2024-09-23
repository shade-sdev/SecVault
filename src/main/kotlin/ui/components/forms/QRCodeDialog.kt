package ui.components.forms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.SecVaultDialog
import ui.theme.Font
import ui.theme.primary
import ui.theme.tertiary

@Composable
fun QRCodeDialog(
    qrCodeDialogState: MutableState<Boolean>,
    bitmapPainter: BitmapPainter
) {
    SecVaultDialog(
        onDismissRequest = { qrCodeDialogState.value = false },
        modifier = Modifier.fillMaxWidth()
                .width(50.dp)
                .height(400.dp),
        roundedSize = 20.dp,
        backgroundColor = tertiary
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.weight(8f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.weight(2f),
                        color = Color.White,
                        text = "Scan QR Code",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 22.sp,
                        fontFamily = Font.RussoOne
                    )
                    Image(
                        painter = bitmapPainter,
                        contentDescription = "QR Code",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                                .height(300.dp)
                                .weight(8f)
                    )
                }
            }

            Spacer(Modifier.height(15.dp))

            Row(
                modifier = Modifier.weight(1.5f),
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {},
                    modifier = Modifier.width(175.dp),
                    colors = ButtonColors(
                        containerColor = primary,
                        contentColor = Color.White,
                        disabledContentColor = primary,
                        disabledContainerColor = primary,
                    )
                )
                {
                    Text(
                        color = Color.White,
                        text = "Save QRCode",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne
                    )
                }
                Button(
                    onClick = { qrCodeDialogState.value = false },
                    modifier = Modifier.width(175.dp),
                    colors = ButtonColors(
                        containerColor = primary,
                        contentColor = Color.White,
                        disabledContentColor = primary,
                        disabledContainerColor = primary
                    )
                )
                {
                    Text(
                        text = "Close",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne
                    )
                }
            }

        }
    }
}