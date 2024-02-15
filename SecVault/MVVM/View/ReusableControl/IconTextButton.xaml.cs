using System.Windows;

namespace SecVault.MVVM.View.ReusableControl;

public partial class IconTextButton
{
    public IconTextButton()
    {
        InitializeComponent();
    }

    #region Font

    public new static readonly DependencyProperty ContentProperty =
        DependencyProperty.Register(nameof(Content), typeof(string), typeof(IconTextButton));

    public new string Content
    {
        get => (string)GetValue(ContentProperty);
        set => SetValue(ContentProperty, value);
    }

    public new static readonly DependencyProperty FontSizeProperty =
        DependencyProperty.Register(nameof(FontSize), typeof(string), typeof(IconTextButton));

    public new string FontSize
    {
        get => (string)GetValue(FontSizeProperty);
        set => SetValue(FontSizeProperty, value);
    }

    #endregion

    #region Icon

    public static readonly DependencyProperty IconSourceProperty =
        DependencyProperty.Register(nameof(IconSource), typeof(string), typeof(IconTextButton));

    public string IconSource
    {
        get => (string)GetValue(IconSourceProperty);
        set => SetValue(IconSourceProperty, value);
    }

    public static readonly DependencyProperty HoverIconSourceProperty =
        DependencyProperty.Register(nameof(HoverIconSource), typeof(string), typeof(IconTextButton));

    public string HoverIconSource
    {
        get => (string)GetValue(HoverIconSourceProperty);
        set => SetValue(HoverIconSourceProperty, value);
    }

    #endregion

    #region Colors

    public new static readonly DependencyProperty BackgroundProperty =
        DependencyProperty.Register(nameof(Background), typeof(string), typeof(IconTextButton));

    public new string Background
    {
        get => (string)GetValue(BackgroundProperty);
        set => SetValue(BackgroundProperty, value);
    }

    public static readonly DependencyProperty PressedBackgroundProperty =
        DependencyProperty.Register(nameof(PressedBackground), typeof(string), typeof(IconTextButton));

    public string PressedBackground
    {
        get => (string)GetValue(PressedBackgroundProperty);
        set => SetValue(PressedBackgroundProperty, value);
    }

    public static readonly DependencyProperty HoverBackgroundProperty =
        DependencyProperty.Register(nameof(HoverBackground), typeof(string), typeof(IconTextButton));

    public string HoverBackground
    {
        get => (string)GetValue(HoverBackgroundProperty);
        set => SetValue(HoverBackgroundProperty, value);
    }

    public new static readonly DependencyProperty ForegroundProperty =
        DependencyProperty.Register(nameof(Foreground), typeof(string), typeof(IconTextButton));

    public new string Foreground
    {
        get => (string)GetValue(ForegroundProperty);
        set => SetValue(ForegroundProperty, value);
    }

    public static readonly DependencyProperty HoverForegroundProperty =
        DependencyProperty.Register(nameof(HoverForeground), typeof(string), typeof(IconTextButton));

    public string HoverForeground
    {
        get => (string)GetValue(HoverForegroundProperty);
        set => SetValue(HoverForegroundProperty, value);
    }

    #endregion

    #region Dimensions

    public new static readonly DependencyProperty WidthProperty =
        DependencyProperty.Register(nameof(Width), typeof(int), typeof(IconTextButton));

    public new int Width
    {
        get => (int)GetValue(WidthProperty);
        set => SetValue(WidthProperty, value);
    }

    public new static readonly DependencyProperty HeightProperty =
        DependencyProperty.Register(nameof(Height), typeof(int), typeof(IconTextButton));

    public new int Height
    {
        get => (int)GetValue(HeightProperty);
        set => SetValue(HeightProperty, value);
    }

    #endregion
}