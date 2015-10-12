
#ifndef SPEAKERTEST_H
#define SPEAKERTEST_H

#include <cppunit/extensions/HelperMacros.h>
#include "hello/Speaker.hpp"

class SpeakerTest : public CppUnit::TestFixture
{
  CPPUNIT_TEST_SUITE( SpeakerTest );
  CPPUNIT_TEST( testSayHello );
  CPPUNIT_TEST_SUITE_END();

public:
  void setUp();
  void tearDown();

  void testSayHello();
};

#endif  // MONEYTEST_H
