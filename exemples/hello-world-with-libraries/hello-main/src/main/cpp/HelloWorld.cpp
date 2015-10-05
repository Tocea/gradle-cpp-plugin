#include <Speaker.hpp>

using namespace std;
using namespace Hello;

int main(int argc, char *argv[]) {
  Speaker* speaker = new Speaker();

  cout << speaker->sayHello();  
}
