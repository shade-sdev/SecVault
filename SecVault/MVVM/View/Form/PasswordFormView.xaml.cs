using System.Windows.Input;

namespace SecVault.MVVM.View.Form;

public partial class PasswordFormView
{
    public PasswordFormView()
    {
        InitializeComponent();
    }

    private void OnWindow_Drag(object sender, MouseEventArgs e)
    {
        if (e.LeftButton == MouseButtonState.Pressed) DragMove();
    }
}