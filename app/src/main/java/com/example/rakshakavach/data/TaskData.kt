package com.example.rakshakavach.data

data class SafetyTask(
    val id: String,
    val name: String,
    val description: String,
    val requiredGear: List<SafetyGear>,
    val riskLevel: RiskLevel
)

data class SafetyGear(
    val name: String,
    val iconName: String // Simple string to map to an icon later
)

enum class RiskLevel(val label: String, val severityPoints: Int) {
    LOW("Minor cuts or bruises", 1),
    MEDIUM("Fractures or severe burns", 3),
    HIGH("Fatal or permanent disability", 5)
}

object TaskRepository {
    val tasks = listOf(
        SafetyTask(
            id = "t1",
            name = "Welding",
            description = "Joining metals using high heat.",
            requiredGear = listOf(
                SafetyGear("Welding Helmet", "helmet"),
                SafetyGear("Heat-resistant Gloves", "gloves"),
                SafetyGear("Safety Boots", "boots"),
                SafetyGear("Fire-retardant Apron", "apron")
            ),
            riskLevel = RiskLevel.HIGH
        ),
        SafetyTask(
            id = "t2",
            name = "Digging Trench",
            description = "Excavating ground for foundations or pipes.",
            requiredGear = listOf(
                SafetyGear("Hard Hat", "hardhat"),
                SafetyGear("High-Vis Vest", "vest"),
                SafetyGear("Steel-toe Boots", "boots"),
                SafetyGear("Safety Goggles", "goggles")
            ),
            riskLevel = RiskLevel.MEDIUM
        ),
        SafetyTask(
            id = "t3",
            name = "Working at Height",
            description = "Scaffolding, roofing, or tall structures.",
            requiredGear = listOf(
                SafetyGear("Safety Harness", "harness"),
                SafetyGear("Hard Hat", "hardhat"),
                SafetyGear("Non-slip Boots", "boots")
            ),
            riskLevel = RiskLevel.HIGH
        ),
        SafetyTask(
            id = "t4",
            name = "Material Handling",
            description = "Lifting and moving heavy materials.",
            requiredGear = listOf(
                SafetyGear("Thick Gloves", "gloves"),
                SafetyGear("Steel-toe Boots", "boots"),
                SafetyGear("Back Support Belt", "belt")
            ),
            riskLevel = RiskLevel.LOW
        )
    )
}
