import scala.sys.process.{ProcessLogger, _}
import java.io.{File, PrintWriter}

import com.typesafe.scalalogging.StrictLogging
import org.apache.commons.io.FileUtils
import play.twirl.api.{Template0, TxtFormat}

object Main extends StrictLogging {

  def main(args: Array[String]): Unit = {
    FileUtils.copyDirectory(new File("src/main/static"), latexDir)
    latex(tex.tex_input)
    pdf(latex(tex.main))
  }

  val latexDir = new File("target/latex")

  def latex(template: Template0[TxtFormat.Appendable]) = {
    val file =
      new File(latexDir, s"${template.getClass.getSimpleName.dropRight(1)}.tex")
    val out = new PrintWriter(file, "UTF-8")
    out println template.render().toString
    out.close()
    file
  }

  def pdf(file: File) = {
    Process(
      Seq("xelatex", file.getAbsolutePath),
      latexDir
    ).!(
      ProcessLogger(
        CLaTeXOutputFilter.ParseLine(file.getName),
        strLine =>
          if (
            strLine != s"stdin -> ${file.getName.dropRight(4)}.pdf" &&
            strLine
              .exists(char => !char.isDigit && char != '[' && char != ']') &&
            !strLine.matches("\\d+ bytes written") &&
            strLine != "xdvipdfmx:warning: Object @page. already defined." &&
            strLine != "xdvipdfmx:warning: Object @page.1 already defined." &&
            strLine != "xdvipdfmx:warning: PDF destination \"Hfootnote.1\" not defined"
          )
            logger.error(s"${file.getName}: '$strLine'")
      )
    )
  }

}
