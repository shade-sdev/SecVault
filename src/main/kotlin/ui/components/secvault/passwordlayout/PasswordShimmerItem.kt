package ui.components.secvault.passwordlayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ui.components.ShimmerShape

@Composable
fun PasswordShimmerItem() {
    Row(
        modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .padding(PaddingValues(start = 5.dp, end = 5.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            ShimmerShape(
                shape = CircleShape,
                modifier = Modifier.fillMaxSize(),
                radius = 14f
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(8.5f).fillMaxHeight().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy((4).dp, Alignment.CenterVertically)
        ) {
            Row {
                ShimmerShape(
                    shape = RectangleShape,
                    modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(14.dp),
                    radius = null
                )
            }
            Row {
                ShimmerShape(
                    shape = RectangleShape,
                    modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .height(14.dp),
                    radius = null
                )
            }
        }
        Column(
            modifier = Modifier.weight(0.5f).fillMaxHeight().fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            ShimmerShape(
                shape = CircleShape,
                modifier = Modifier
                        .fillMaxWidth()
                        .height(14.dp),
                radius = 5f
            )
        }
    }

}