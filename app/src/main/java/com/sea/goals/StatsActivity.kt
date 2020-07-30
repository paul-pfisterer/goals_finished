package com.sea.goals

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LegendEntry
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_stats.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class StatsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawer: DrawerLayout
    private lateinit var db: AppDatabase
    private var dayNames = ArrayList<String>()
    private var dayDone = ArrayList<Double>()

    private var weekNames = ArrayList<String>()
    private var weekDone = ArrayList<Double>()

    private var challengeNames = ArrayList<String>()
    private var challengeDone = ArrayList<Double>()

    private lateinit var dailyGoalsTest: List<Daily>
    private lateinit var weeklyGoalsTest: List<Weekly>
    private lateinit var challengeGoalsTest: List<Challenge>

    private lateinit var dailyList: ArrayList<IntRange>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)
        db = AppDatabase.getDatabase(this)
        //Set ActionBar, Enable toggle Navigation Drawer, Add Navigation Listener
        setSupportActionBar(toolbar as Toolbar?)
        (toolbar as Toolbar?)?.title = "Today"
        drawer = drawer_layout
        nav_view.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar as Toolbar?, 0, 0
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()


        chartData.setNoDataText("")
        chartDataChallenge.setNoDataText("")
        chartDataWeekly.setNoDataText("")

        //Setup Database
        CoroutineScope(IO).launch {
            val dailyGoals: List<Daily> = db.dailyGoals().getAll()
            val weeklyGoals: List<Weekly> = db.weeklyGoals().getAll()
            val challengeGoals: List<Challenge> = db.challengeGoals().getAll()
            CoroutineScope(Main).launch {
                finishedLoading(dailyGoals, weeklyGoals, challengeGoals)

            }
        }



    }

    fun finishedLoading(dailyGoals: List<Daily>, weeklyGoals: List<Weekly>, challengeGoals: List<Challenge>) {
        dailyGoalsTest = dailyGoals
        weeklyGoalsTest = weeklyGoals
        challengeGoalsTest = challengeGoals

        dailyGoals.forEach {
            dayNames.add(it.name)
            dayDone.add(it.monday)
            dayDone.add(it.tuesday)
            dayDone.add(it.wednesday)
            dayDone.add(it.thursday)
            dayDone.add(it.friday)
            dayDone.add(it.saturday)
            dayDone.add(it.sunday)
        }

        weeklyGoals.forEach{
            weekNames.add(it.name)
            weekDone.add(it.monday)
            weekDone.add(it.tuesday)
            weekDone.add(it.wednesday)
            weekDone.add(it.thursday)
            weekDone.add(it.friday)
            weekDone.add(it.saturday)
            weekDone.add(it.sunday)
        }

        challengeGoals.forEach{
            challengeNames.add(it.name)
            challengeDone.add(it.monday)
            challengeDone.add(it.tuesday)
            challengeDone.add(it.wednesday)
            challengeDone.add(it.thursday)
            challengeDone.add(it.friday)
            challengeDone.add(it.saturday)
            challengeDone.add(it.sunday)
        }

        graphDaily()
        graphWeekly()
        graphChallenge()


    }

    // statistic challenge goals
    fun graphChallenge(){
        var barChartView = findViewById<BarChart>(R.id.chartDataChallenge)
        val barWidth: Float

        barWidth = 0.5f

        var xAxisValues = ArrayList<String>()
        xAxisValues.add("Mon")
        xAxisValues.add("Die")
        xAxisValues.add("Mi")
        xAxisValues.add("Do")
        xAxisValues.add("Fr")
        xAxisValues.add("Sa")
        xAxisValues.add("So")

        var yValueGroup1 = ArrayList<BarEntry>()

        // draw the graph
        var barDataSet1: BarDataSet

        var start = 0
        var end = 6

        var size = challengeDone.size

        var value1 = 0.0
        var value2 = 0.0
        var value3 = 0.0
        var value4 = 0.0
        var value5 = 0.0
        var value6 = 0.0
        var value7 = 0.0


        // for every different goal
        for(j in 0 until challengeNames.size) {

            if (!challengeDone.isEmpty()) {

                // everyday of the week (monday - sunday)
                for (i in start..end) {

                    // check how many goals the user has and get values
                    when(size){

                        // 1 goal
                        7 -> {
                            value1 = challengeDone.get(i)
                        }
                        // 2 goals
                        14 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                        }
                        // 3 goals
                        21 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                            value3 = challengeDone.get(i+14)
                        }
                        // 4 goals
                        28 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                            value3 = challengeDone.get(i+14)
                            value4 = challengeDone.get(i+21)
                        }
                        // 5 goals
                        35 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                            value3 = challengeDone.get(i+14)
                            value4 = challengeDone.get(i+21)
                            value5 = challengeDone.get(i+28)
                        }
                        // 6 goals
                        42 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                            value3 = challengeDone.get(i+14)
                            value4 = challengeDone.get(i+21)
                            value5 = challengeDone.get(i+28)
                            value6 = challengeDone.get(i+35)
                        }
                        // 7 goals
                        49 -> {
                            value1 = challengeDone.get(i)
                            value2 = challengeDone.get(i+7)
                            value3 = challengeDone.get(i+14)
                            value4 = challengeDone.get(i+21)
                            value5 = challengeDone.get(i+28)
                            value6 = challengeDone.get(i+35)
                            value7 = challengeDone.get(i+42)
                        }
                    }

                    //allocate values to specific day of the week
                    when (i % 7) {

                        1 -> yValueGroup1.add(BarEntry(2f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        2 -> yValueGroup1.add(BarEntry(3f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat() )))

                        3 -> yValueGroup1.add(BarEntry(4f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        4 -> yValueGroup1.add(BarEntry(5f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        5 -> yValueGroup1.add(BarEntry(6f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        6 -> yValueGroup1.add(BarEntry(7f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        0 -> yValueGroup1.add(BarEntry(1f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                    }

                }

            }


        }


        barDataSet1 = BarDataSet(yValueGroup1, "Test")
        barDataSet1.setColors(Color.CYAN, Color.BLUE, Color.WHITE, ContextCompat.getColor(this, R.color.colorRoyalBlue), ContextCompat.getColor(this, R.color.colorPink), ContextCompat.getColor(this, R.color.colorMediumslateblue), ContextCompat.getColor(this, R.color.colorLightGrey))

        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)


        var barData = BarData(barDataSet1)

        barChartView.data = barData // set the data and list of lables into chart


        barChartView.description.isEnabled = false
        barChartView.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        barChartView.setData(barData)
        barChartView.getBarData().setBarWidth(barWidth)
        barChartView.getXAxis().setAxisMinimum(0f)
        barChartView.getXAxis().setAxisMaximum(7f)

        barChartView.getData().setHighlightEnabled(false)
        barChartView.invalidate()



        // set bar label
        var legend = barChartView.legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)

        var legenedEntries = arrayListOf<LegendEntry>()



        // legend for different color per goal
        if(!challengeNames.isEmpty()){

            for(i in 0 until challengeNames.size){

                when(i%7){

                    0 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.CYAN))

                    1 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.BLUE))

                    2 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.WHITE))

                    3 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorRoyalBlue)))

                    4 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorPink)))

                    5 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorMediumslateblue)))

                    6 -> legenedEntries.add(LegendEntry(challengeNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorLightGrey)))

                }

            }
        }


        // in liste drinnen
        legend.setCustom(legenedEntries)
        legend.setYOffset(0f)
        legend.setXOffset(0f)
        legend.setYEntrySpace(0f)
        legend.setTextSize(5f)
        legend.xEntrySpace = 10f


        // x Axis
        val xAxis = barChartView.getXAxis()
        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(IndexAxisValueFormatter(xAxisValues))

        xAxis.setLabelCount(7)
        xAxis.mAxisMaximum = 7f
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 3f
        xAxis.spaceMax = 3f
    }

    // statistic weekly goals
    fun graphWeekly(){
        var barChartView = findViewById<BarChart>(R.id.chartDataWeekly)
        val barWidth: Float

        barWidth = 0.5f

        var xAxisValues = ArrayList<String>()
        xAxisValues.add("Mon")
        xAxisValues.add("Die")
        xAxisValues.add("Mi")
        xAxisValues.add("Do")
        xAxisValues.add("Fr")
        xAxisValues.add("Sa")
        xAxisValues.add("So")


        var yValueGroup1 = ArrayList<BarEntry>()


        // draw the graph
        var barDataSet1: BarDataSet


        var start = 0
        var end = 6

        var size = weekDone.size

        var value1 = 0.0
        var value2 = 0.0
        var value3 = 0.0
        var value4 = 0.0
        var value5 = 0.0
        var value6 = 0.0
        var value7 = 0.0


        // for every different goal
        for(j in 0 until weekNames.size) {


            if (!weekDone.isEmpty()) {

                // everyday of the week (monday - sunday)
                for (i in start..end) {


                    // check how many goals the user has and get values
                    when(size){

                        // 1 goal
                        7 -> {
                            value1 = weekDone.get(i)
                        }
                        // 2 goals
                        14 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                        }
                        // 3 goals
                        21 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                            value3 = weekDone.get(i+14)
                        }
                        // 4 goals
                        28 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                            value3 = weekDone.get(i+14)
                            value4 = weekDone.get(i+21)
                        }
                        // 5 goals
                        35 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                            value3 = weekDone.get(i+14)
                            value4 = weekDone.get(i+21)
                            value5 = weekDone.get(i+28)
                        }
                        // 6 goals
                        42 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                            value3 = weekDone.get(i+14)
                            value4 = weekDone.get(i+21)
                            value5 = weekDone.get(i+28)
                            value6 = weekDone.get(i+35)
                        }
                        // 7 goals
                        49 -> {
                            value1 = weekDone.get(i)
                            value2 = weekDone.get(i+7)
                            value3 = weekDone.get(i+14)
                            value4 = weekDone.get(i+21)
                            value5 = weekDone.get(i+28)
                            value6 = weekDone.get(i+35)
                            value7 = weekDone.get(i+42)
                        }
                    }


                    //allocate values to specific day of the week
                    when (i % 7) {

                        1 -> yValueGroup1.add(BarEntry(2f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        2 -> yValueGroup1.add(BarEntry(3f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat() )))

                        3 -> yValueGroup1.add(BarEntry(4f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        4 -> yValueGroup1.add(BarEntry(5f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        5 -> yValueGroup1.add(BarEntry(6f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        6 -> yValueGroup1.add(BarEntry(7f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        0 -> yValueGroup1.add(BarEntry(1f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))
                    }
                }
            }

        }



        barDataSet1 = BarDataSet(yValueGroup1, "Test")

        barDataSet1.setColors(Color.CYAN, Color.BLUE, Color.WHITE, ContextCompat.getColor(this, R.color.colorRoyalBlue), ContextCompat.getColor(this, R.color.colorPink), ContextCompat.getColor(this, R.color.colorMediumslateblue), ContextCompat.getColor(this, R.color.colorLightGrey))

        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        var barData = BarData(barDataSet1)
        barChartView.data = barData // set the data and list of lables into chart


        barChartView.description.isEnabled = false
        barChartView.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        barChartView.setData(barData)
        barChartView.getBarData().setBarWidth(barWidth)
        barChartView.getXAxis().setAxisMinimum(0f)
        barChartView.getXAxis().setAxisMaximum(7f)

        barChartView.getData().setHighlightEnabled(false)
        barChartView.invalidate()


        // set bar label
        var legend = barChartView.legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)

        var legenedEntries = arrayListOf<LegendEntry>()



        // legend for different color per goal
        if(!weekNames.isEmpty()){

            for(i in 0 until weekNames.size){

                when(i%7){

                    0 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.CYAN))

                    1 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.BLUE))

                    2 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.WHITE))

                    3 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorRoyalBlue)))

                    4 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorPink)))

                    5 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorMediumslateblue)))

                    6 -> legenedEntries.add(LegendEntry(weekNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorLightGrey)))

                }

            }
        }

        legend.setCustom(legenedEntries)
        legend.setYOffset(0f)
        legend.setXOffset(0f)
        legend.setYEntrySpace(0f)
        legend.setTextSize(5f)
        legend.xEntrySpace = 10f


        // x Axis
        val xAxis = barChartView.getXAxis()
        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(IndexAxisValueFormatter(xAxisValues))

        xAxis.setLabelCount(7)
        xAxis.mAxisMaximum = 7f
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 3f
        xAxis.spaceMax = 3f
    }

    // statistic daily goals
    fun graphDaily() {
        var barChartView = findViewById<BarChart>(R.id.chartData)
        val barWidth: Float

        barWidth = 0.5f


        var xAxisValues = ArrayList<String>()
        xAxisValues.add("Mon")
        xAxisValues.add("Die")
        xAxisValues.add("Mi")
        xAxisValues.add("Do")
        xAxisValues.add("Fr")
        xAxisValues.add("Sa")
        xAxisValues.add("So")


        var yValueGroup1 = ArrayList<BarEntry>()


        var start = 0
        var end = 6

        var size = dayDone.size

        var value1 = 0.0
        var value2 = 0.0
        var value3 = 0.0
        var value4 = 0.0
        var value5 = 0.0
        var value6 = 0.0
        var value7 = 0.0


        // for every different goal
        for(j in 0 until dayNames.size) {

            if (!dayDone.isEmpty()) {

                // everyday of the week (monday - sunday)
                for (i in start..end) {


                    // check how many goals the user has and get values
                    when(size){

                        // 1 goal
                        7 -> {
                            value1 = dayDone.get(i)
                        }
                        // 2 goals
                        14 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                        }
                        // 3 goals
                        21 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                            value3 = dayDone.get(i+14)
                        }
                        // 4 goals
                        28 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                            value3 = dayDone.get(i+14)
                            value4 = dayDone.get(i+21)
                        }
                        // 5 goals
                        35 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                            value3 = dayDone.get(i+14)
                            value4 = dayDone.get(i+21)
                            value5 = dayDone.get(i+28)
                        }
                        // 6 goals
                        42 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                            value3 = dayDone.get(i+14)
                            value4 = dayDone.get(i+21)
                            value5 = dayDone.get(i+28)
                            value6 = dayDone.get(i+35)
                        }
                        // 7 goals
                        49 -> {
                            value1 = dayDone.get(i)
                            value2 = dayDone.get(i+7)
                            value3 = dayDone.get(i+14)
                            value4 = dayDone.get(i+21)
                            value5 = dayDone.get(i+28)
                            value6 = dayDone.get(i+35)
                            value7 = dayDone.get(i+42)
                        }
                    }


                    //allocate values to specific day of the week
                    when (i % 7) {

                        1 -> yValueGroup1.add(BarEntry(2f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        2 -> yValueGroup1.add(BarEntry(3f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat() )))

                        3 -> yValueGroup1.add(BarEntry(4f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        4 -> yValueGroup1.add(BarEntry(5f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        5 -> yValueGroup1.add(BarEntry(6f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        6 -> yValueGroup1.add(BarEntry(7f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))

                        0 -> yValueGroup1.add(BarEntry(1f, floatArrayOf(value1.toFloat(), value2.toFloat(), value3.toFloat(), value4.toFloat(), value5.toFloat(), value6.toFloat(), value7.toFloat())))
                    }
                }
            }

        }


        // draw the graph
        var barDataSet1: BarDataSet

        barDataSet1 = BarDataSet(yValueGroup1, "Test")

        barDataSet1.setColors(Color.CYAN, Color.BLUE, Color.WHITE, ContextCompat.getColor(this, R.color.colorRoyalBlue), ContextCompat.getColor(this, R.color.colorPink), ContextCompat.getColor(this, R.color.colorMediumslateblue), ContextCompat.getColor(this, R.color.colorLightGrey))

        barDataSet1.setDrawIcons(false)
        barDataSet1.setDrawValues(false)

        var barData = BarData(barDataSet1)

        barChartView.data = barData // set the data and list of lables into chart


        barChartView.description.isEnabled = false
        barChartView.description.textSize = 0f
        barData.setValueFormatter(LargeValueFormatter())
        barChartView.setData(barData)
        barChartView.getBarData().setBarWidth(barWidth)
        barChartView.getXAxis().setAxisMinimum(0f)
        barChartView.getXAxis().setAxisMaximum(7f)

        barChartView.getData().setHighlightEnabled(false)
        barChartView.invalidate()


        // set bar label
        var legend = barChartView.legend
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        legend.setDrawInside(false)
        legend.xEntrySpace = 20f

        var legenedEntries = arrayListOf<LegendEntry>()



        // legend for different color per goal
        if(!dayNames.isEmpty()){
            for(i in 0 until dayNames.size){
                when(i%7){
                    0 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.CYAN))
                    1 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.BLUE))
                    2 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, Color.WHITE))
                    3 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorRoyalBlue)))
                    4 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorPink)))
                    5 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorMediumslateblue)))
                    6 -> legenedEntries.add(LegendEntry(dayNames.get(i), Legend.LegendForm.SQUARE, 15f, 15f, null, ContextCompat.getColor(this, R.color.colorLightGrey)))
                }
            }
        }

        legend.setCustom(legenedEntries)
        legend.setYOffset(0f)
        legend.setXOffset(0f)
        legend.setYEntrySpace(0f)
        legend.setTextSize(5f)
        legend.xEntrySpace = 10f


        // x Axis
        val xAxis = barChartView.getXAxis()
        xAxis.setGranularity(1f)
        xAxis.setGranularityEnabled(true)
        xAxis.setCenterAxisLabels(true)
        xAxis.setDrawGridLines(false)
        xAxis.textSize = 9f

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        xAxis.setValueFormatter(IndexAxisValueFormatter(xAxisValues))

        xAxis.setLabelCount(7)
        xAxis.mAxisMaximum = 7f
        xAxis.setCenterAxisLabels(true)
        xAxis.setAvoidFirstLastClipping(true)
        xAxis.spaceMin = 3f
        xAxis.spaceMax = 3f


    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var intent: Intent
        when(item.itemId) {
            R.id.createGoal -> {
                intent = Intent(this, CreateGoalActivity::class.java)
                startActivity(intent)
            }
            R.id.today -> {
                intent = Intent(this, TodayActivity::class.java)
                startActivity(intent)
            }
            R.id.stats -> {
                intent = Intent(this, StatsActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }


}