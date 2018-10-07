import java.util.*

class Travel(numberOfCities: Int) {

    private var travel = ArrayList<City>()
    private var previousTravel = ArrayList<City>()

    val distance: Int
        get() {
            var distance = 0
            for (index in travel.indices) {
                val starting = getCity(index)
                val destination: City
                if (index + 1 < travel.size) {
                    destination = getCity(index + 1)
                } else {
                    destination = getCity(0)
                }
                distance += starting.distanceToCity(destination).toInt()
            }
            return distance
        }

    init {
        for (i in 0 until numberOfCities) {
            travel.add(City())
        }
    }

    fun generateInitialTravel() {
        if (travel.isEmpty())
            Travel(10)
        travel.shuffle()
    }

    fun swapCities() {
        val a = generateRandomIndex()
        val b = generateRandomIndex()
        previousTravel = travel
        val x = travel[a]
        val y = travel[b]
        travel[a] = y
        travel[b] = x
    }

    fun revertSwap() {
        travel = previousTravel
    }

    private fun generateRandomIndex(): Int {
        return (Math.random() * travel.size).toInt()
    }

    private fun getCity(index: Int): City {
        return travel[index]
    }
}