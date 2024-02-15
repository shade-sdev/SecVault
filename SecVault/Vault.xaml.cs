using System.Windows.Input;

namespace SecVault;

/// <summary>
///     Interaction logic for MainWindow.xaml
/// </summary>
public partial class Vault
{
    public Vault()
    {
        InitializeComponent();
    }

    private void OnWindow_Drag(object sender, MouseEventArgs e)
    {
        if (e.LeftButton == MouseButtonState.Pressed) DragMove();
    }
}