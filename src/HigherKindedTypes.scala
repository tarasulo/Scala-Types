object HigherKindedTypes extends App {

  /* ETL pipeline design contains two input types: List & Seq
  Map contains additional values for adding with keys "list" and "seq".
  write the implementation of the method "transform" и "write"
  write calls transform
  the transform method should add value to the end of the list or to the head of seq
  */

  trait BatchRun[M[_]] {
    def write[A](item: A, db: M[A]): M[A] = transform(item, db)
    def transform[A](item: A, db: M[A]): M[A]
  }

  val newData = Map("list" -> "data 3", "seq" -> 3)

  val listData: List[String] = List("data 1", "data 2")
  val listBatchRun = new BatchRun[List] {
    def transform[A](item: A, db: List[A]): List[A] = db :+ item
  }
  val savedList = listBatchRun.write(newData("list"), listData)


  val seqData: Seq[Int] = Seq(1, 2)
  val seqBatchRun = new BatchRun[Seq] {
    def transform[A](item: A, db: Seq[A]): Seq[A] = item +: db
  }
  val savedSeq = seqBatchRun.write(newData("seq"), seqData)

  println(savedList)
  println(savedList == List("data 1", "data 2", "data 3"))
  println(savedSeq)
  println(savedSeq == Seq(3, 1, 2))

}
