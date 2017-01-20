# stp

Simple Text Processor (or Scala Text Processor). This is an experiment on
combining [LaTeX](https://www.latex-project.org/) with
[Scala](http://www.scala-lang.org/), [sbt](http://www.scala-sbt.org/) and
[Twirl templates](https://github.com/playframework/twirl) for text processing.

## Requirements

To run the project you will need:

* [sbt](http://www.scala-sbt.org/) installed
* `xelatex` command available from command line — you need to install a
  [TeX distributiuon](http://www.tug.org/interest.html#free)

## Running

To generate PDF output just execute

```
sbt run
```

You will need Internet access for sbt and LaTeX to load all the dependencies

## Why?

Although LaTeX is a great text processing tool with extensive library of
packages covering virtually any use case and an active community, writing your
own extensions and automations for LaTeX is usually a complicated task since TeX
was not intended to be a handy programming language.

Our approach enables one to use Scala to create extensions and automations in a
simple, declarative, object-oriented, functional and type-safe way. Since we are
mostly dealing with string literals it is convinient to use a templating engine
— we use Twirl. To automate the build process and dependency management we use
sbt.

## How It Works

LaTeX file templates should be placed in [src/main/twirl](src/main/twirl). These
templates are compiled by Twirl.

PDF output is generated by running the project — i.e. by executing
[`Main.main`](src/main/scala/Main.scala#L10). First, TeX source files should be
produced from templates — this is done by calling

```
latex(tex.mytemplate)
```

It returns a `File` object for the generated file.

To generate the PDF output from a TeX source file you should apply `pdf` to this
file. The resulting file will be placed in `target/latex` folder. This is the
folder where generated TeX sources are stored and processed by `xelatex`.

Any static files (TeX sources, style files, images etc.) that do not need to be
preprocessed by Twirl should be placed in [src/main/static](src/main/static).

While executing `xelatex` command we filter out errors and warnings and log them
to the console. Filtering is done using
[regexes](src/main/scala/CLaTeXOutputFilter.scala) taken from [TeXnicCenter](htt
ps://sourceforge.net/p/texniccenter/code/ci/default/tree/TeXnicCenter/LatexOutpu
tFilter.cpp) sources.