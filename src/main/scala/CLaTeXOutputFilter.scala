import com.typesafe.scalalogging.StrictLogging

//-------------------------------------------------------------------
// class CLaTeXOutputFilter
//-------------------------------------------------------------------

object CLaTeXOutputFilter extends StrictLogging {

  def ParseLine(filename: String)(strLine: String) = {
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // parse for new error, warning or bad box

    //Catching Errors
    val error1 = "^! LaTeX Error: (.*)$".r
    val error2 = "^! (.*)$".r

    //Catching Warnings

    // Catches Latex and package warnings
    val warning1 = ".+warning.*: (.*) on input line ([[:digit:]]+)".r

    // Catches LaTeX and package warnings that split over several lines
    val warning2 = ".+warning.*: (.*)".r

    // Catches LaTeX and package warnings that split over several lines
    val packageWarning = "Package\\s+(.+)\\s+warning.*: (.*)".r

    // Catches LaTeX and package warnings that split over several lines
    val classWarning = "Class\\s+(.+)\\s+warning.*: (.*)".r

    //Catching Bad Boxes
    val badBox1 = "^(Over|Under)full \\\\[hv]box .* at lines ([[:digit:]]+)--([[:digit:]]+)".r
    //Use the following only, if you know how to get the source line for it.
    // This is not simple, as TeX is not reporting it.
    val badBox2 = "^(Over|Under)full \\\\[hv]box .* has occurred while \\\\output is active".r

    //Catching the source line numbers of error/warning types
    // which are reported over more than one line in the output
    val line1 = "l\\.([[:digit:]]+)".r
    val line2 = "line ([[:digit:]]+)".r

    //Catching the number of output pages
    val output1 = "Output written on .* \\((\\d*) page.*\\)".r
    val output2 = "No pages of output".r

    /* ABOUT THE ORDER OF SEARCHING THE OUTPUT

      Every TeX-User needs to correct an error, if he wants to have a valid document.
      Therefore, errors are quite rare.
      For some warnings, but not all, there is also a need to correct them - to get a better document.
      Therefore, warnings may occur more often than errors.
      Most users are not going to correct a bad box. Sometimes it is not possible at all (at least
      for the average TeX-user).
      Therefore, bad boxes are the most common type of catched entities.

      To speed up parsing the output, it is preferable to look for the common stuff first.
      ==> 1. bad boxes; 2. warnings; 3. errors; 4. srcline-numbers; 5. other stuff

    */

    if (warning1.findFirstIn(strLine).nonEmpty)
      logger warn s"$filename: [warning1] $strLine"
    else if (packageWarning.findFirstIn(strLine).nonEmpty)
      logger warn s"$filename: [packageWarning] $strLine"
    else if (warning2.findFirstIn(strLine).nonEmpty)
      logger warn s"$filename: [warning2] $strLine"
    else if (error1.findFirstIn(strLine).nonEmpty)
      logger error s"$filename: [error1] $strLine"
    else if (error2.findFirstIn(strLine).nonEmpty)
      logger error s"$filename: [error2] $strLine"

  }

}
