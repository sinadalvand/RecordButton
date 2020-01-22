# RecordButton

[![Developer](https://img.shields.io/badge/developer-sina%20dalvand-orange)](https://github.com/sinadalvand)
[![Lisense](https://img.shields.io/badge/License-Apache%202-lightgrey.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://img.shields.io/github/v/release/sinadalvand/RecordButton)](https://bintray.com/sinadalvand/maven/RecordButton)

Record Button by customization option (VideoRecord,AudioRecord,VoideRecord,Button,Hold)


<img src="https://github.com/sinadalvand/RecordButton/blob/master/art/logo.png" width="100"/>


<img src="https://github.com/sinadalvand/RecordButton/blob/master/art/demo.gif" width="250"/>

<img src="https://github.com/sinadalvand/RecordButton/blob/master/art/screen.png" width="250"/>


## Gradle & Maven
```
 implementation 'ir.sinadalvand.libs:recordbutton:{Last_release_version}'
```
```
<dependency>
	<groupId>ir.sinadalvand.libs</groupId>
	<artifactId>recordbutton</artifactId>
	<version>1.0.0</version>
	<type>pom</type>
</dependency>
```


## Xml usage :
```
 <ir.sinadalvand.recordbutton.RecordButton
        android:id="@+id/recordButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:rb_backColor="#FFF"
        app:rb_buttonColor="#FFF"
        app:rb_progressbarColor="#FFC107"
        app:rb_progressbarWidth="4dp"
        app:rb_progressTime="5000" => milliseconds 
		/>
```

### Java/Kotlin Usage :
```

		// button front color
        recordButton.setButtonColor(Color.WHITE)

        // button back color
        recordButton.setBackColor(Color.WHITE)

        // button progress color
        recordButton.setProgressColor(Color.parseColor("#FFC107"))

        // button progress percent (0-100) float
        recordButton.setPercent(0f)

        // button progress width pixel
        recordButton.setProgressWidth(5)

        // button finish time
        recordButton.setProgressTime(5000)

```

### Listener (RecordButtonListener) :
```

		// after holing until end this will call
            override fun onFinish() {

            }
			

            // if wanna start Progressing return True else false (some where is useful ex.getting permission)
            override fun onStartProgress(): Boolean {


             // return false //==> make it lock
                return true
            }
			

            // return progressing percent
            override fun onProgressing(percent: Float?) {

            }
			

            // this will call if user release button before end on progress
            override fun onCancell() {

            }

```


# License

    Copyright 2019 Sina Dalvand

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

