using System.Collections.ObjectModel;
using SecVault.Core;
using SecVault.MVVM.Model.Password;
using SecVault.MVVM.Model.PasswordCategory;
using SecVault.MVVM.Model.PasswordSort;

namespace SecVault.MVVM.ViewModel;

public class SecVaultViewModel : ObservableObject
{
    private PasswordCategory? _selectedCategory;
    private PasswordSort?     _selectedPasswordSort;

    public SecVaultViewModel()
    {
        _selectedCategory     = PasswordCategories.FirstOrDefault();
        _selectedPasswordSort = PasswordSorts.FirstOrDefault();
        ClickCommand          = new RelayCommand(o => { Console.WriteLine("Jazz"); });
        SortCommand           = new RelayCommand(ExecuteSortCommand);
    }

    public ObservableCollection<Password> Passwords          { get; } = ConstantService.GetPasswords();
    public List<PasswordCategory>         PasswordCategories { get; } = ConstantService.GetPasswordCategories();
    public List<PasswordSort>             PasswordSorts      { get; } = ConstantService.GetPasswordSorts();

    public RelayCommand ClickCommand { get; set; }
    public RelayCommand SortCommand  { get; set; }

    public PasswordCategory? SelectedCategory
    {
        get => _selectedCategory;
        set
        {
            _selectedCategory = value;
            OnPropertyChanged();
        }
    }

    public PasswordSort? SelectedPasswordSort
    {
        get => _selectedPasswordSort;
        set
        {
            _selectedPasswordSort = value;
            OnPropertyChanged();
        }
    }

    private void ExecuteSortCommand(object parameter)
    {
        if (parameter is PasswordSortType categoryType) Console.WriteLine(categoryType);
    }
}