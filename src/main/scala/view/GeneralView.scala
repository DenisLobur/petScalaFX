package view

trait GeneralView {

  def updateTotalRespondents(updatedValue: String)

  def updateMedian(median: String)

  def updateQ1(quartileOne: String)

  def updateQ3(quartileThree: String)

}
