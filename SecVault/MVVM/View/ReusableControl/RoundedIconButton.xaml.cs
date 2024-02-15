using System.Windows;

namespace SecVault.MVVM.View.ReusableControl;

public partial class RoundedIconButton
{
    public RoundedIconButton()
    {
        InitializeComponent();
    }

    #region Icon

    public static readonly DependencyProperty IconSourceProperty =
        DependencyProperty.Register(nameof(IconSource), typeof(string), typeof(RoundedIconButton));

    public string IconSource
    {
        get => (string)GetValue(IconSourceProperty);
        set => SetValue(IconSourceProperty, value);
    }

    public static readonly DependencyProperty HoverIconSourceProperty =
        DependencyProperty.Register(nameof(HoverIconSource), typeof(string), typeof(RoundedIconButton));

    public string HoverIconSource
    {
        get => (string)GetValue(HoverIconSourceProperty);
        set => SetValue(HoverIconSourceProperty, value);
    }

    #endregion

    #region Colors

    public static readonly DependencyProperty PressedBackgroundProperty =
        DependencyProperty.Register(nameof(PressedBackground), typeof(string), typeof(RoundedIconButton));

    public string PressedBackground
    {
        get => (string)GetValue(PressedBackgroundProperty);
        set => SetValue(PressedBackgroundProperty, value);
    }

    public static readonly DependencyProperty HoverBackgroundProperty =
        DependencyProperty.Register(nameof(HoverBackground), typeof(string), typeof(RoundedIconButton));

    public string HoverBackground
    {
        get => (string)GetValue(HoverBackgroundProperty);
        set => SetValue(HoverBackgroundProperty, value);
    }

    #endregion
}