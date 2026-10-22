$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
if (-not $env:JAVA_HOME) { throw 'Set JAVA_HOME to your installed JDK first.' }
$java = Join-Path $env:JAVA_HOME 'bin/java.exe'
$javac = Join-Path $env:JAVA_HOME 'bin/javac.exe'
$target = Join-Path $root 'build/regression-checks'
New-Item -ItemType Directory -Force -Path $target | Out-Null
$source = Join-Path $root 'app/src/main/java/com/example/smartpantry'
$files = @('Ingredient.java','Recipe.java','RecipeIngredient.java','IngredientRules.java','MatchUtils.java') | ForEach-Object { Join-Path $source $_ }
& $javac -d $target @files (Join-Path $root 'tests/RegressionChecks.java')
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
& $java -cp $target RegressionChecks
if ($LASTEXITCODE -ne 0) { throw 'Regression checks failed.' }
