File Structure:

```

social-contract-android/
│
├── README.md
├── LICENSE
├── .gitignore
├── gradlew
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── settings.gradle.kts
├── build.gradle.kts
├── gradle.properties
│
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   │
│   └── src/
│       ├── main/
│       │   │
│       │   ├── AndroidManifest.xml
│       │   │
│       │   ├── java/
│       │   │   └── com/
│       │       └── socialcontract/
│       │           │
│       │           ├── MainActivity.kt
│       │           │
│       │           ├── SocialContractApplication.kt
│       │           │
│       │           ├── navigation/
│       │           │   ├── AppNavigation.kt
│       │           │   ├── Routes.kt
│       │           │   └── NavigationState.kt
│       │           │
│       │           ├── data/
│       │           │   │
│       │           │   ├── database/
│       │           │   │   ├── AppDatabase.kt
│       │           │   │   ├── DatabaseMigrations.kt
│       │           │   │   │
│       │           │   │   ├── dao/
│       │           │   │   │   ├── ContractDao.kt
│       │           │   │   │   ├── PartyDao.kt
│       │           │   │   │   ├── LandDao.kt
│       │           │   │   │   ├── ExpenseDao.kt
│       │           │   │   │   ├── HarvestDao.kt
│       │           │   │   │   ├── EvidenceDao.kt
│       │           │   │   │   └── SettlementDao.kt
│       │           │   │   │
│       │           │   │   └── entities/
│       │           │   │       ├── ContractEntity.kt
│       │           │   │       ├── PartyEntity.kt
│       │           │   │       ├── LandEntity.kt
│       │           │   │       ├── CultivationEntity.kt
│       │           │   │       ├── ExpenseEntity.kt
│       │           │   │       ├── HarvestEntity.kt
│       │           │   │       ├── EvidenceEntity.kt
│       │           │   │       └── SettlementEntity.kt
│       │           │   │
│       │           │   └── repository/
│       │           │       ├── ContractRepository.kt
│       │           │       ├── ExpenseRepository.kt
│       │           │       ├── HarvestRepository.kt
│       │           │       ├── EvidenceRepository.kt
│       │           │       └── SettlementRepository.kt
│       │           │
│       │           ├── domain/
│       │           │   │
│       │           │   ├── model/
│       │           │   │   ├── Contract.kt
│       │           │   │   ├── Party.kt
│       │           │   │   ├── Land.kt
│       │           │   │   ├── Cultivation.kt
│       │           │   │   ├── Expense.kt
│       │           │   │   ├── Harvest.kt
│       │           │   │   ├── Evidence.kt
│       │           │   │   └── Settlement.kt
│       │           │   │
│       │           │   ├── enums/
│       │           │   │   ├── ContractStatus.kt
│       │           │   │   ├── ExpenseCategory.kt
│       │           │   │   ├── PaymentPayer.kt
│       │           │   │   ├── EvidenceType.kt
│       │           │   │   ├── HarvestUnit.kt
│       │           │   │   └── SettlementStatus.kt
│       │           │   │
│       │           │   └── usecase/
│       │           │       ├── CreateContractUseCase.kt
│       │           │       ├── UpdateContractUseCase.kt
│       │           │       ├── AddExpenseUseCase.kt
│       │           │       ├── AddEvidenceUseCase.kt
│       │           │       ├── RecordHarvestUseCase.kt
│       │           │       ├── CalculateSettlementUseCase.kt
│       │           │       └── CompleteContractUseCase.kt
│       │           │
│       │           ├── calculation/
│       │           │   ├── CostCalculator.kt
│       │           │   ├── HarvestValueCalculator.kt
│       │           │   ├── SettlementCalculator.kt
│       │           │   └── ShareValidator.kt
│       │           │
│       │           ├── ui/
│       │           │   │
│       │           │   ├── theme/
│       │           │   │   ├── Color.kt
│       │           │   │   ├── Theme.kt
│       │           │   │   └── Type.kt
│       │           │   │
│       │           │   ├── home/
│       │           │   │   ├── HomeScreen.kt
│       │           │   │   └── HomeViewModel.kt
│       │           │   │
│       │           │   ├── contract/
│       │           │   │   ├── ContractListScreen.kt
│       │           │   │   ├── ContractDetailScreen.kt
│       │           │   │   ├── CreateContractScreen.kt
│       │           │   │   ├── ContractViewModel.kt
│       │           │   │   └── ContractComponents.kt
│       │           │   │
│       │           │   ├── parties/
│       │           │   │   ├── PartyScreen.kt
│       │           │   │   └── PartyViewModel.kt
│       │           │   │
│       │           │   ├── land/
│       │           │   │   ├── LandScreen.kt
│       │           │   │   └── LandViewModel.kt
│       │           │   │
│       │           │   ├── cultivation/
│       │           │   │   ├── CultivationScreen.kt
│       │           │   │   ├── CultivationTimelineScreen.kt
│       │           │   │   └── CultivationViewModel.kt
│       │           │   │
│       │           │   ├── expenses/
│       │           │   │   ├── ExpenseLedgerScreen.kt
│       │           │   │   ├── AddExpenseScreen.kt
│       │           │   │   ├── ExpenseDetailScreen.kt
│       │           │   │   └── ExpenseViewModel.kt
│       │           │   │
│       │           │   ├── harvest/
│       │           │   │   ├── HarvestScreen.kt
│       │           │   │   ├── RecordHarvestScreen.kt
│       │           │   │   └── HarvestViewModel.kt
│       │           │   │
│       │           │   ├── evidence/
│       │           │   │   ├── EvidenceScreen.kt
│       │           │   │   ├── AddEvidenceScreen.kt
│       │           │   │   └── EvidenceViewModel.kt
│       │           │   │
│       │           │   ├── settlement/
│       │           │   │   ├── SettlementScreen.kt
│       │           │   │   ├── SettlementBreakdownScreen.kt
│       │           │   │   └── SettlementViewModel.kt
│       │           │   │
│       │           │   └── contractpreview/
│       │           │       ├── ContractPreviewScreen.kt
│       │           │       ├── ContractDocument.kt
│       │           │       └── ContractExport.kt
│       │           │
│       │           ├── export/
│       │           │   ├── PdfContractGenerator.kt
│       │           │   ├── ContractShareManager.kt
│       │           │   └── CsvExportManager.kt
│       │           │
│       │           ├── validation/
│       │           │   ├── ContractValidator.kt
│       │           │   ├── ExpenseValidator.kt
│       │           │   └── SettlementValidator.kt
│       │           │
│       │           └── util/
│       │               ├── CurrencyFormatter.kt
│       │               ├── DateFormatter.kt
│       │               ├── IdGenerator.kt
│       │               └── NumberFormatter.kt
│       │
│       ├── res/
│       │   ├── drawable/
│       │   │   └── ic_launcher_foreground.xml
│       │   │
│       │   ├── mipmap/
│       │   │   └── ...
│       │   │
│       │   ├── values/
│       │   │   ├── strings.xml
│       │   │   ├── colors.xml
│       │   │   └── themes.xml
│       │   │
│       │   └── xml/
│       │       ├── backup_rules.xml
│       │       └── data_extraction_rules.xml
│       │
│       └── test/
│           └── java/
│               └── com/
│                   └── socialcontract/
│                       ├── calculation/
│                       │   ├── CostCalculatorTest.kt
│                       │   ├── HarvestValueCalculatorTest.kt
│                       │   ├── SettlementCalculatorTest.kt
│                       │   └── ShareValidatorTest.kt
│                       │
│                       ├── validation/
│                       │   ├── ContractValidatorTest.kt
│                       │   └── ExpenseValidatorTest.kt
│                       │
│                       └── domain/
│                           └── ContractTest.kt
│
├── docs/
│   ├── architecture.md
│   ├── contract-model.md
│   ├── expense-model.md
│   ├── settlement-model.md
│   ├── evidence-model.md
│   └── legal-research.md
│
└── .github/
    └── workflows/
        ├── android-ci.yml
        └── release.yml
