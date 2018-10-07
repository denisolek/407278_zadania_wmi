class City {

    private val x: Int = (Math.random() * 500).toInt()
    private val y: Int = (Math.random() * 500).toInt()

    fun distanceToCity(city: City): Double {
        val x = Math.abs(this.x - city.x)
        val y = Math.abs(this.y - city.y)
        return Math.sqrt(Math.pow(x.toDouble(), 2.0) + Math.pow(y.toDouble(), 2.0))
    }
}