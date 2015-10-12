// ? #include "stdafx.h"

#include "hello/SpeakerTest.hpp"

using namespace Hello;
// Registers the fixture into the 'registry'
CPPUNIT_TEST_SUITE_REGISTRATION( SpeakerTest );


void
SpeakerTest::setUp()
{
}


void
SpeakerTest::tearDown()
{
}


void
SpeakerTest::testSayHello()
{
  // // Given a speaker
  Speaker* speaker = new Speaker();


  // when the speaker say hello.
  string sentence = speaker->sayHello();

  // then he must say 'hello world:
  string expected = "Hello, world!\n";
  CPPUNIT_ASSERT_EQUAL( expected, sentence );
}
