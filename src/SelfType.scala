object SelfType extends App {
  trait TestEnvironment {
    val testName: String
  }

  class TestExecutor { test: TestEnvironment =>
    def execute(): Unit = {
      println(s"Executing ${test.testName}")
    }
  }

  val aTest: TestExecutor = new TestExecutor with TestEnvironment {
    override val testName: String = "T-test"
  }

  aTest.execute()

  //cyclic reference
  class A {self: B => }
  class B {self: A => }

  //Cake pattern
  trait User {
    def info(user: String): String = user
  }

  trait AppComponent {
    self: User =>
    // can use info, declared in User
    def detailedInfo(user: String): String = "Detailed info for" + info(user)
  }

  trait Application {self: AppComponent => }

  //layer 1 - detail different API
  trait RegisteredUser extends User
  trait AnonymousUser extends User

  //layer 2 - mix different components
  trait Analytics extends AppComponent with AnonymousUser
  trait Profile extends AppComponent with RegisteredUser




//Type Classes
  trait Fruit[A] {
    def buy(a: A): List[A]
  }

  class Apple
  object Apple {
    implicit object ApplePurchase extends Fruit[Apple] {
      override def buy(a: Apple): List[Apple] = {
        println("bought an apple")
        List()
      }
    }
  }


  implicit class PurchaseOps[A](fruit: A) {
    def buy(implicit fruitTypeClassInstance: Fruit[A]): List[A] =
      fruitTypeClassInstance.buy(fruit)
  }

  val apple = new Apple
  apple.buy

  trait Menu[A] {
    def order(a: A): List[A]
  }

  class VegetarianMenu
  object VegetarianMenu {
    implicit object VegetarianOrder extends Menu[VegetarianMenu] {
      override def order(a: VegetarianMenu): List[VegetarianMenu] = {
        println("vegetarian order")
        List()
      }
    }
  }

  class StandardMenu
  object StandardMenu {
    implicit object StandardOrder extends Menu[StandardMenu] {
      override def order(a: StandardMenu): List[StandardMenu] = {
        println("standard order")
        List()
      }
    }
  }

  implicit class Order[A](menu: A) {
    def order(implicit menuTypeClassInstance: Menu[A]): List[A] =
      menuTypeClassInstance.order(menu)
  }
}
