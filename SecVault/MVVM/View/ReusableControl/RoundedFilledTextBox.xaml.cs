using System.Windows;

namespace SecVault.MVVM.View.ReusableControl;

public partial class RoundedFilledTextBox
{
    public RoundedFilledTextBox()
    {
        InitializeComponent();
    }

    #region Icon

    public static readonly DependencyProperty IconSourceProperty =
        DependencyProperty.Register(nameof(IconSource), typeof(string), typeof(RoundedFilledTextBox));

    public string IconSource
    {
        get => (string)GetValue(IconSourceProperty);
        set => SetValue(IconSourceProperty, value);
    }

    #endregion
}