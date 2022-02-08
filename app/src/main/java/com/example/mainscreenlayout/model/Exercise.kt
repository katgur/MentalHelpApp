package com.example.mainscreenlayout.model

class Exercise(val name: String, override var current: Int = 0) : IIterator {

    private var keys: ArrayList<String> = ArrayList()
    private var values: ArrayList<String> = ArrayList()

    var efficiency: String = ""
        set(value) {
            keys.add("efficiency")
            values.add(value)
            field = value
        }

    var duration: String = ""
        set(value) {
            keys.add("duration")
            values.add(value)
            field = value
        }

    var steps: List<String> = listOf()
        set(value) {
            for (i in 1..value.size) {
                keys.add("step$i")
                values.add(value[i])
            }
            field = value
        }

    var additional: String = ""
        set(value) {
            keys.add("additional")
            values.add(value)
            field = value
        }

    override fun hasNext(): Boolean {
        return current < keys.size
    }

    override fun getNext(): Pair<String, String> {
        if (hasNext()) {
            current++
            return Pair(keys[current], values[current])
        }
        return Pair("", "")
    }
}