package util

object PollPeriod {

  sealed trait PollPeriod {
    def name: String

    def show: String
  }

  case object December2011 extends PollPeriod {
    val name = "2011december"
    val show = "December 2011"
  }

  case object May2011 extends PollPeriod {
    val name = "2011may"
    val show = "May 2011"
  }

  case object December2012 extends PollPeriod {
    val name = "2012december"
    val show = "December 2012"
  }

  case object May2012 extends PollPeriod {
    val name = "2012may"
    val show = "May 2012"
  }

  case object December2013 extends PollPeriod {
    val name = "2013december"
    val show = "December 2013"
  }

  case object May2013 extends PollPeriod {
    val name = "2013may"
    val show = "May 2013"
  }

  case object December2014 extends PollPeriod {
    val name = "2014december"
    val show = "December 2014"
  }

  case object May2014 extends PollPeriod {
    val name = "2014may"
    val show = "May 2014"
  }

  case object December2015 extends PollPeriod {
    val name = "2015december"
    val show = "December 2015"
  }

  case object May2015 extends PollPeriod {
    val name = "2015may"
    val show = "May 2015"
  }

  case object December2016 extends PollPeriod {
    val name = "2016december"
    val show = "December 2016"
  }

  case object May2016 extends PollPeriod {
    val name = "2016may"
    val show = "May 2016"
  }

  case object December2017 extends PollPeriod {
    val name = "2017december"
    val show = "December 2017"
  }

  case object May2017 extends PollPeriod {
    val name = "2017may"
    val show = "May 2017"
  }

}