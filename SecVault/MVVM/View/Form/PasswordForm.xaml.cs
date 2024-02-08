using System.Windows;
using System.Windows.Input;

namespace SecVault.MVVM.View.Form;

public partial class PasswordForm : Window
{
    public PasswordForm()
    {
        InitializeComponent();
    }
    
    private void OnWindow_Drag(object sender, MouseEventArgs e)
    {
        if (e.LeftButton == MouseButtonState.Pressed)
        {
            DragMove();
        }
    }
}