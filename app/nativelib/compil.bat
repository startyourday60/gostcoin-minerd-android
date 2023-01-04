export ANDROID_NDK_HOME="/cygdrive/c/Users/oruge/AppData/Local/Android/Sdk"
#/cygdrive/c/Users/oruge/AppData/Local/Android/Sdk/ndk/25.1.8937393/toolchains/llvm/prebuilt/windows-x86_64/sysroot
export ANDROID_TOOLCHAIN="$ANDROID_NDK_HOME/ndk/25.1.8937393/toolchains/llvm/prebuilt/windows-x86_64/bin"
export CC="$ANDROID_NDK_HOME/ndk/25.1.8937393/toolchains/llvm/prebuilt/windows-x86_64/bin/armv7a-linux-androideabi23-clang++"
# android C:\Users\oruge\AndroidStudioProjects\MyManer\app\nativelib\curl-android-ios-master\prebuilt-with-ssl\android\include
# "-Icurl-android-ios-master/prebuilt-with-ssl/android/include"
# -L.
# ./configure --host=armv7a --with-libcurl=curl-android-ios-master/prebuilt-with-ssl/android/
#  ln -s $ANDROID_NDK_SYSROOT /cygdrive/c/Users/oruge/AppData/Local/Android/Sdk/platforms/android-33/arch-arm
export PATH=$ANDROID_TOOLCHAIN:$PATH
# cp $ANDROID_TOOLCHAIN/armv7a-linux-androideabi21-clang $ANDROID_TOOLCHAIN/arm-linux-androideabi-gcc
# /cygdrive/c/Users/oruge/AppData/Local/Android/Sdk/ndk/25.1.8937393/prebuilt/windows-x86_64/bin/make.exe
# ./Configure android-arm no-shared no-ssl3 no-comp no-hw no-engine
/cygdrive/c/Users/oruge/AppData/Local/Android/Sdk/ndk/25.1.8937393/toolchains/llvm/prebuilt/windows-x86_64/bin/armv7a-linux-androideabi23-clang  -Icurl-android-ios-master/prebuilt-with-ssl/android/include  -L/. -o minerd minerd-cpu-miner.ominerd-util.o minerd-sha2.o minerd-sha2-arm.o minerd-sha2-x86.o minerd-sha2-x64.o minerd-scrypt.o minerd-scrypt-arm.o minerd-scrypt-x86.o minerd-scrypt-x64.o minerd-blake.o minerd-bmw.o minerd-groestl.o minerd-jh.o minerd-keccak.o minerd-skein.o minerd-aes_helper.o minerd-cubehash.o minerd-shavite.o minerd-simd.o minerd-echo.o minerd-luffa.o minerd-quark.o minerd-gost.o minerd-Xcoin.o compat/jansson/libjansson.a libcurl.a