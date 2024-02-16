using System.Windows;
using System.Windows.Controls;
using SecVault.Core;

namespace SecVault.MVVM.View.ReusableControl;

public partial class FormDateTextBox
{
    public FormDateTextBox()
    {
        InitializeComponent();
    }

    #region Commands

    public static readonly DependencyProperty CommandProperty =
        DependencyProperty.Register(nameof(Command), typeof(RelayCommand), typeof(FormDateTextBox));

    public RelayCommand Command
    {
        get => (RelayCommand)GetValue(CommandProperty);
        set => SetValue(CommandProperty, value);
    }

    #endregion

    #region Dimensions

    public new static readonly DependencyProperty WidthProperty =
        DependencyProperty.Register(nameof(Width), typeof(int), typeof(FormDateTextBox));

    public new int Width
    {
        get => (int)GetValue(WidthProperty);
        set => SetValue(WidthProperty, value);
    }
    
    public new static readonly DependencyProperty BoxOneWidthProperty =
        DependencyProperty.Register(nameof(BoxOneWidth), typeof(int), typeof(FormDateTextBox));

    public new int BoxOneWidth
    {
        get => (int)GetValue(BoxOneWidthProperty);
        set => SetValue(BoxOneWidthProperty, value);
    }
    
    public new static readonly DependencyProperty BoxTwoWidthProperty =
        DependencyProperty.Register(nameof(BoxTwoWidth), typeof(int), typeof(FormDateTextBox));

    public new int BoxTwoWidth
    {
        get => (int)GetValue(BoxTwoWidthProperty);
        set => SetValue(BoxTwoWidthProperty, value);
    }

    public new static readonly DependencyProperty HeightProperty =
        DependencyProperty.Register(nameof(Height), typeof(int), typeof(FormDateTextBox));

    public new int Height
    {
        get => (int)GetValue(HeightProperty);
        set => SetValue(HeightProperty, value);
    }

    #endregion

    #region Validation

    public static readonly DependencyProperty ValidProperty =
        DependencyProperty.Register(nameof(Valid), typeof(bool?), typeof(FormDateTextBox));

    public bool? Valid
    {
        get => (bool?)GetValue(ValidProperty);
        set => SetValue(ValidProperty, value);
    }

    #endregion

    #region Labels

    public static readonly DependencyProperty TextBoxLabelProperty =
        DependencyProperty.Register(nameof(TextBoxLabel), typeof(string), typeof(FormDateTextBox));

    public string TextBoxLabel
    {
        get => (string)GetValue(TextBoxLabelProperty);
        set => SetValue(TextBoxLabelProperty, value);
    }

    public static readonly DependencyProperty ValidationLabelProperty =
        DependencyProperty.Register(nameof(ValidationLabel), typeof(string), typeof(FormDateTextBox));

    public string ValidationLabel
    {
        get => (string)GetValue(ValidationLabelProperty);
        set => SetValue(ValidationLabelProperty, value);
    }

    #endregion

    #region Content

    public static readonly DependencyProperty TextBoxOneContentProperty =
        DependencyProperty.Register(nameof(TextBoxOneContent), typeof(string), typeof(FormDateTextBox));

    public string TextBoxOneContent
    {
        get => (string)GetValue(TextBoxOneContentProperty);
        set => SetValue(TextBoxOneContentProperty, value);
    }
    
    public static readonly DependencyProperty TextBoxTwoContentProperty =
        DependencyProperty.Register(nameof(TextBoxTwoContent), typeof(string), typeof(FormDateTextBox));

    public string TextBoxTwoContent
    {
        get => (string)GetValue(TextBoxTwoContentProperty);
        set => SetValue(TextBoxTwoContentProperty, value);
    }

    public static readonly DependencyProperty TextBoxOneIconProperty =
        DependencyProperty.Register(nameof(TextOneBoxIcon), typeof(object), typeof(FormDateTextBox));

    public object TextOneBoxIcon
    {
        get => GetValue(TextBoxOneIconProperty);
        set => SetValue(TextBoxOneIconProperty, value);
    }
    
    public static readonly DependencyProperty TextBoxTwoIconProperty =
        DependencyProperty.Register(nameof(TextTwoBoxIcon), typeof(object), typeof(FormDateTextBox));

    public object TextTwoBoxIcon
    {
        get => GetValue(TextBoxTwoIconProperty);
        set => SetValue(TextBoxTwoIconProperty, value);
    }
    
    public static readonly DependencyProperty TextBoxOnePlaceHolderProperty =
        DependencyProperty.Register(nameof(TextOneBoxPlaceHolder), typeof(string), typeof(FormDateTextBox));

    public string TextOneBoxPlaceHolder
    {
        get => (string)GetValue(TextBoxOnePlaceHolderProperty);
        set => SetValue(TextBoxOnePlaceHolderProperty, value);
    }
    
    public static readonly DependencyProperty TextBoxTwoPlaceHolderProperty =
        DependencyProperty.Register(nameof(TextTwoBoxPlaceHolder), typeof(string), typeof(FormDateTextBox));

    public string TextTwoBoxPlaceHolder
    {
        get => (string)GetValue(TextBoxTwoPlaceHolderProperty);
        set => SetValue(TextBoxTwoPlaceHolderProperty, value);
    }

    #endregion

    #region Font

    public static readonly DependencyProperty TextBoxFontFamilyProperty =
        DependencyProperty.Register(nameof(TextBoxFontFamily), typeof(string), typeof(FormDateTextBox));

    public string TextBoxFontFamily
    {
        get => (string)GetValue(TextBoxFontFamilyProperty);
        set => SetValue(TextBoxFontFamilyProperty, value);
    }

    #endregion
}