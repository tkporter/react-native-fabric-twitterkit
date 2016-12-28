# react-native-fabric-twitterkit
React Native Fabric Twitter-kit support for iOS and Android

Use [react-native-fabric](https://github.com/corymsmith/react-native-fabric) for Answers and Crashlytics

# Installation:

```
npm install react-native-fabric-twitterkit --save
rnpm link react-native-fabric-twitterkit
```

## iOS

Follow the official Fabric iOS instructions on [Fabric.io](https://docs.fabric.io/apple/twitter/installation.html)

## Android

Follow "Set Up Kit" from official Fabric Android docs at [Fabric.io](https://docs.fabric.io/android/twitter/compose-tweets.html)

Navigate to your `MainActivity.java` somewhere in `MyApp/android/app/src/main/java/...../MainActivity.java`

```diff
+ import com.tkporter.fabrictwitterkit.FabricTwitterKitPackage;

...

public class MainActivity extends ReactActivity {

	.....

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
+ 		FabricTwitterKitPackage.getInstance().onActivityResult(this, requestCode, resultCode, data);
	}

	...

}
```

Go to your `MyAppApplication.java` inside the same folder as `MainActivity.java`

```diff
+ import com.tkporter.fabrictwitterkit.FabricTwitterKitPackage;

...

public final MyApp extends ....... {

	...

	@Override List<ReactPackage> getPackages() {
		return Arrays.<ReactPackage>asList(
			...
+			FabricTwitterKitPackage.getInstance(),
			...
		);
	}

	...

}
```

# Usage

This package has iOS and Android functionality, so you can use the same call for each platform.

There are lots of functions, and not a lot of README writing time. Check out `FabricTwitterKit/FabricTwitterKit.m` and `Android/src/main/java/com/tkporter/fabrictwitterkit/FabricTwitterKitModule.java` for the other supported functions! :)

ComposeTweet example:

```JavaScript
import FabricTwitterKit from 'react-native-fabric-twitterkit'

...

mySuperCoolFunction() {

	FabricTwitterKit.composeTweet({
		body: 'react-native-fabric-twitterkit is awesome!'
	}, (completed, cancelled, error) => {
		console.log('completed: ' + completed + ' cancelled: ' + cancelled + ' error: ' + error);
	});

}

```
