package com.huzhipeng.coin.utils

object MathUtils {
    /**
     *
     *  * @描述:变异性量数：方差 <br></br>
     *  * @方法名: variance <br></br>
     *  * @param in <br></br>
     *  * @return <br></br>
     *  * @返回类型 double <br></br>
     *  * @创建人 micheal <br></br>
     *  * @创建时间 2019年1月2日下午10:47:27 <br></br>
     *  * @修改人 micheal <br></br>
     *  * @修改时间 2019年1月2日下午10:47:27 <br></br>
     *  * @修改备注 <br></br>
     *  * @since <br></br>
     *  * @throws <br></br>
     *  
     */
    fun variance(array: MutableList<Long>): Double {
        val average = average(array)
        var total = 0
        for (i in array.indices) {
            total += ((array[i] - average) * (array[i] - average)).toInt()   //求出方差，如果要计算方差的话这一步就可以了
        }
        return (total / array.size).toDouble()
    }

    //去掉交易量大于80 的秒，再求平均值
    fun average(array: MutableList<Long>): Double {
        var sum = 0.toLong()
        for (i in array.filter { it < 80 }.indices) {
            sum += array[i]      //求出数组的总和
        }
        return (sum / array.filter { it < 80 }.size).toDouble()
    }

    /**
     *
     *  * @描述:变异性量数：标准差（无偏估计） <br></br>
     *  * @方法名: standardDeviation <br></br>
     *  * @param in <br></br>
     *  * @return <br></br>
     *  * @返回类型 double <br></br>
     *  * @创建人 micheal <br></br>
     *  * @创建时间 2019年1月2日下午10:32:07 <br></br>
     *  * @修改人 micheal <br></br>
     *  * @修改时间 2019年1月2日下午10:32:07 <br></br>
     *  * @修改备注 <br></br>
     *  * @since <br></br>
     *  * @throws <br></br>
     *  
     */
    fun standardDeviation(list: MutableList<Long>): Double {
        return Math.sqrt(variance(list))
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val array = longArrayOf(73, 4, 2, 0, 3, 16, 3, 1, 8, 0).toMutableList()
        println(standardDeviation(array))
        println(average(array))
        val array1 = longArrayOf(81, 8, 5, 1, 4).toMutableList()
        println(standardDeviation(array1))
        println(average(array1))

    }

    fun reachLow(list: MutableList<Long>) : Boolean {
        var count = 0
        list.forEach {
            if (it > 1) {
                count++
            }
        }
        return count > 5
    }
}
