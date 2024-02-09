using System.Windows;
using SecVault.Core;

namespace SecVault.MVVM.View.ReusableControl;

public sealed partial class FormTextBox
{
    #region Commands

    public static readonly DependencyProperty CommandProperty =
        DependencyProperty.Register(nameof(Command), typeof(RelayCommand), typeof(FormTextBox));

    public RelayCommand Command
    {
        get => (RelayCommand)GetValue(CommandProperty);
        set => SetValue(CommandProperty, value);
    }

    #endregion

    #region Dimensions

    public new static readonly DependencyProperty WidthProperty =
        DependencyProperty.Register(nameof(Width), typeof(int), typeof(FormTextBox));

    public new int Width
    {
        get => (int)GetValue(WidthProperty);
        set => SetValue(WidthProperty, value);
    }

    public new static readonly DependencyProperty HeightProperty =
        DependencyProperty.Register(nameof(Height), typeof(int), typeof(FormTextBox));

    public new int Height
    {
        get => (int)GetValue(HeightProperty);
        set => SetValue(HeightProperty, value);
    }

    #endregion

    #region Validation

    public static readonly DependencyProperty ValidProperty =
        DependencyProperty.Register(nameof(Valid), typeof(bool?), typeof(FormTextBox));

    public bool? Valid
    {
        get => (bool?)GetValue(ValidProperty);
        set => SetValue(ValidProperty, value);
    }

    #endregion

    #region Labels

    public static readonly DependencyProperty TextBoxLabelProperty =
        DependencyProperty.Register(nameof(TextBoxLabel), typeof(string), typeof(FormTextBox));

    public string TextBoxLabel
    {
        get => (string)GetValue(TextBoxLabelProperty);
        set => SetValue(TextBoxLabelProperty, value);
    }

    public static readonly DependencyProperty ValidationLabelProperty =
        DependencyProperty.Register(nameof(ValidationLabel), typeof(string), typeof(FormTextBox));

    public string ValidationLabel
    {
        get => (string)GetValue(ValidationLabelProperty);
        set => SetValue(ValidationLabelProperty, value);
    }

    #endregion

    #region Content

    public static readonly DependencyProperty TextBoxContentProperty =
        DependencyProperty.Register(nameof(TextBoxContent), typeof(string), typeof(FormTextBox));

    public string TextBoxContent
    {
        get => (string)GetValue(TextBoxContentProperty);
        set => SetValue(TextBoxContentProperty, value);
    }

    public static readonly DependencyProperty TextBoxIconProperty =
        DependencyProperty.Register(nameof(TextBoxIcon), typeof(object), typeof(FormTextBox));

    public object TextBoxIcon
    {
        get => GetValue(TextBoxIconProperty);
        set => SetValue(TextBoxIconProperty, value);
    }

    #endregion

    public FormTextBox()
    {
        InitializeComponent();
    }
}