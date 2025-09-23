package com.example.unittestsworkshop.data.api

import com.example.unittestsworkshop.data.models.User

object DataProvider {
    val users = mutableListOf(
        User(
            id = "1",
            username = "alice",
            email = "alice@example.com",
            friends = listOf("2", "3")
        ),
        User(
            id = "2",
            username = "bob",
            email = "bob@example.com",
            friends = listOf("1", "4")
        ),
        User(
            id = "3",
            username = "charlie",
            email = "charlie@example.com",
            friends = listOf("1", "4", "5")
        ),
        User(
            id = "4",
            username = "diana",
            email = "diana@example.com",
            friends = listOf("2", "3")
        ),
        User(
            id = "5",
            username = "eve",
            email = "eve@example.com",
            friends = listOf("3")
        ),
        User(
            id = "6",
            username = "frank",
            email = "frank@example.com",
            friends = listOf("7", "8")
        ),
        User(
            id = "7",
            username = "grace",
            email = "grace@example.com",
            friends = listOf("6", "9")
        ),
        User(
            id = "8",
            username = "henry",
            email = "henry@example.com",
            friends = listOf("6", "10")
        ),
        User(
            id = "9",
            username = "isabella",
            email = "isabella@example.com",
            friends = listOf("7", "11")
        ),
        User(
            id = "10",
            username = "jack",
            email = "jack@example.com",
            friends = listOf("8", "12")
        ),
        User(
            id = "11",
            username = "katherine",
            email = "katherine@example.com",
            friends = listOf("9", "13")
        ),
        User(
            id = "12",
            username = "liam",
            email = "liam@example.com",
            friends = listOf("10", "14")
        ),
        User(
            id = "13",
            username = "mia",
            email = "mia@example.com",
            friends = listOf("11", "15")
        ),
        User(
            id = "14",
            username = "noah",
            email = "noah@example.com",
            friends = listOf("12", "16")
        ),
        User(
            id = "15",
            username = "olivia",
            email = "olivia@example.com",
            friends = listOf("13", "17")
        ),
        User(
            id = "16",
            username = "peter",
            email = "peter@example.com",
            friends = listOf("14", "18")
        ),
        User(
            id = "17",
            username = "quinn",
            email = "quinn@example.com",
            friends = listOf("15", "19")
        ),
        User(
            id = "18",
            username = "rachel",
            email = "rachel@example.com",
            friends = listOf("16", "20")
        ),
        User(
            id = "19",
            username = "samuel",
            email = "samuel@example.com",
            friends = listOf("17", "21")
        ),
        User(
            id = "20",
            username = "taylor",
            email = "taylor@example.com",
            friends = listOf("18", "22")
        ),
        User(
            id = "21",
            username = "uma",
            email = "uma@example.com",
            friends = listOf("19", "23")
        ),
        User(
            id = "22",
            username = "victor",
            email = "victor@example.com",
            friends = listOf("20", "24")
        ),
        User(
            id = "23",
            username = "wendy",
            email = "wendy@example.com",
            friends = listOf("21", "25")
        ),
        User(
            id = "24",
            username = "xavier",
            email = "xavier@example.com",
            friends = listOf("22")
        ),
        User(
            id = "25",
            username = "yara",
            email = "yara@example.com",
            friends = listOf("23")
        )
    )
}