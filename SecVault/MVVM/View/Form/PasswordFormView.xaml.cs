using System.Windows;
using System.Windows.Input;

namespace SecVault.MVVM.View.Form;

public partial class PasswordFormView : Window
{
    public PasswordFormView()
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