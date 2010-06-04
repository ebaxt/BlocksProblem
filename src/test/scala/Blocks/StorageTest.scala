package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert

@RunWith(classOf[JUnit4])
class StorageTest {
  @Test def shouldInitializeColumns() {
    val list = List(Column("a"), Column("b"))
    val storage = new Storage(list)
    Assert.assertEquals(list, storage.columns)
  }

  @Test def shouldEqualOnColumns() {
    Assert.assertEquals(new Storage(List(Column("a"))), new Storage(List(Column("a"))))
    Assert.assertEquals((new Storage(List(Column("a")))).hashCode, (new Storage(List(Column("a")))).hashCode)
    Assert.assertFalse((new Storage(List(Column("a")))).equals(new Storage(List(Column("f")))))
  }

  @Test def factoryShouldPopulateStorage() {
    Assert.assertEquals(new Storage(List(Column("a"))), Storage(Column("a")))
    Assert.assertEquals(new Storage(List(Column("a"), Column("b", "c"))), Storage(Column("a"), Column("b", "c")))
  }
}