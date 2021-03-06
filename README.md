# Indicators Step ui lib
[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[ ![Download](https://api.bintray.com/packages/kiathee/maven/indicatorsteplib/images/download.svg?version=1.0) ](https://bintray.com/kiathee/maven/indicatorsteplib/1.0/link)

This library can fast and easy develop indicators and step progress ui.

ScreenShot
----------------
![ScreenShot](https://github.com/cheekiat/IndicatorsStep/blob/master/screenshort.gif)

Java Code
----------------
```
        StepProgress mStepProgress = (StepProgress) findViewById(R.id.step_progress);
        
        mStepProgress.setDotCount(5);
```
        
Xml Code
----------------
```
        <com.cheekiat.indicatorsteplib.StepProgress
            android:id="@+id/indicators_progress"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            stepUi:indicator_mode="indicators" />

        <com.cheekiat.indicatorsteplib.StepProgress
            android:id="@+id/step_progress"
            android:layout_width="wrap_content"
            android:layout_margin="16dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            stepUi:indicator_mode="step" />
```

How to use?
----------------
### Java
| Public methods | Description |
| ------------- | ------------- |
| selected(int position) | Set select dot. |
| setDotsOnClickListener(DotOnClickListener onClickListener) | Add click dot listener. |
| setupWithViewPager(ViewPager mViewPager) | Auto change selected dot by view page position |

### Xml
| XML attributes | Description |
| ------------- | ------------- |
| app:dotDefaultSize | Set default dot size. |
| app:dotSelectedSize | Set selected dot size. |
| app:itemMargins | Set margins between dot. |
| app:mode | Set display mode indicators, step. |
| app:selectedDotColor | Set selected dot color. |
| app:unselectDotColor | Set unselect dot color. |
| app:barHeight | Set step bar height. |
| app:unselectTextColor | Set step text size. |

Download
----------------
```
repositories {
  mavenCentral() // jcenter() works as well because it pulls from Maven Central
}

dependencies {
 compile 'com.cheekiat:indicatorsteplib:1.9'
}
```

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)
