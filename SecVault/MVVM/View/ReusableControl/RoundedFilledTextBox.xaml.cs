using System.Windows;

namespace SecVault.MVVM.View.ReusableControl;

public partial class RoundedFilledTextBox
{
    #region Icon

    public new static readonly DependencyProperty IconSourceProperty =
        DependencyProperty.Register(nameof(IconSource), typeof(string), typeof(RoundedFilledTextBox));

    public new string IconSource
    {
        get => (string)GetValue(IconSourceProperty);
        set => SetValue(IconSourceProperty, value);
    }

    #endregion

    public RoundedFilledTextBox()
    {
        InitializeComponent();
    }
}