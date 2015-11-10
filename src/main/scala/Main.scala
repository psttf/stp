import scala.sys.process._
import java.io.{File, PrintWriter}

object Main {
  def main(args: Array[String]): Unit = {
    val dir = new File("target/latex")
    dir.mkdirs
    val file = new File(dir, "main.tex")
    val out = new PrintWriter(file)
    out println tex.main().toString
    out.close()
    Process(
      Seq("pdflatex", file.getAbsolutePath),
      dir
    ).lineStream
  }
}
