package com.ufpe.if710.quentinhas.provider

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.ufpe.if710.quentinhas.R
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.FRIDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.MONDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.SATURDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.SUNDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.THURSDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.TITLE
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.TUESDAY
import com.ufpe.if710.quentinhas.provider.ChartsActivity.Companion.WEDNESDAY
import java.util.ArrayList

class ChartOrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_orders)

        val anyChartView = findViewById<AnyChartView>(R.id.chart_view_order)
        anyChartView.setProgressBar(findViewById<ProgressBar>(R.id.progress_bar))

        val cartesian = AnyChart.column()

        val extras = intent.extras!!

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry(resources.getString(R.string.sunday), extras.getInt(SUNDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.monday), extras.getInt(MONDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.tuesday), extras.getInt(TUESDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.wednesday), extras.getInt(WEDNESDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.thursday), extras.getInt(THURSDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.friday), extras.getInt(FRIDAY)))
        data.add(ValueDataEntry(resources.getString(R.string.saturday), extras.getInt(SATURDAY)))

        val column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("{%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title(extras.getString(TITLE))

        cartesian.yScale().minimum(0.0)

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title(resources.getString(R.string.xAxis))
        cartesian.yAxis(0).title(resources.getString(R.string.yAxis))

        anyChartView.setChart(cartesian)
    }
}
