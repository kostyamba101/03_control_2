const val TYPE_1 = "Mastercard"
const val TYPE_2 = "Maestro"
const val TYPE_3 = "Visa"
const val TYPE_4 = "Мир"
const val TYPE_5 = "VK Pay"

const val LIMIT = 75_000_00
const val OVER_THE_LIMIT_1 = 0.006
const val OVER_THE_LIMIT_2 = 20_00

const val PERCENTAGE_FOR_TYPE_3_4 = 0.0075
const val MIN_COMMISSION = 35_00

fun main(args: Array<String>) {
    val amountPreviousTransfers = 0
    val type = TYPE_5
    val amount = 75600_00
    println(calcCommission(amount,type, amountPreviousTransfers))
}

fun convert(amount: Int): String {
    return "${(amount / 100)} руб. ${amount % 100} коп. "
}

fun calcCommission(amount: Int, type: String, amountPreviousTransfers: Int): String {

    when (type){
        TYPE_1, TYPE_2 -> {
            val commissionForType12: Double = amount * OVER_THE_LIMIT_1 + OVER_THE_LIMIT_2
            if((amountPreviousTransfers + amount) in (0..LIMIT)){
                val transferAmount = amount
                return "Сумма перевода " + convert(amount) +
                        "Комиссия составит 0 руб. " +
                        "Сумма перевода с учетом комиссии " + convert(transferAmount)
            }
            else {
                val transferAmount = amount - (amount * OVER_THE_LIMIT_1 + OVER_THE_LIMIT_2)
                return "Сумма перевода " + convert(amount) +
                        "Комиссия составит " + convert(commissionForType12.toInt()) +
                        "Сумма перевода с учетом комиссии " + convert(transferAmount.toInt())
            }
        }
        TYPE_3, TYPE_4 -> {
            val commissionForType34: Double = amount * PERCENTAGE_FOR_TYPE_3_4
            if (commissionForType34 < MIN_COMMISSION) {
                val transferAmount = amount - MIN_COMMISSION
                return "Сумма перевода " + convert(amount) +
                        "Комиссия составит " + (MIN_COMMISSION / 100) + " руб. " +
                        "Сумма перевода с учетом комиссии " + (transferAmount / 100) + " руб." + (amount % 100) + " коп. "
            } else {
                val transferAmount = amount - commissionForType34
                return "Сумма перевода " + (amount / 100) + " руб. " + (amount % 100) + " коп. " +
                        "Комиссия составит " + convert(commissionForType34.toInt()) +
                        "Сумма перевода с учетом комиссии " + convert(transferAmount.toInt())
            }
        }
        TYPE_5 -> {
            val transferAmount = amount
            return "Сумма перевода " + convert(amount) +
                    "Комиссия составит 0 руб. " +
                    "Сумма перевода с учетом комиссии " + convert(transferAmount)
        }
        else -> {
            return "Возникла ошибка при переводе"
        }
    }
}
