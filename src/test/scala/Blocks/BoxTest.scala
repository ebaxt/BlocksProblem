package Blocks

import org.scalatest.junit.{ShouldMatchersForJUnit}
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import junit.framework.Assert

@RunWith(classOf[JUnit4])
class BoxTest  {
  @Test def shouldInitializeName() {
    Assert.assertEquals("a", new Box("a").name)
  }

  @Test def shouldEqualOnName() {
    Assert.assertEquals(new Box("a"), new Box("a"))
    Assert.assertEquals(new Box("a").hashCode, new Box("a").hashCode)
    Assert.assertFalse(new Box("a").equals(new Box("b")))
  }
}
