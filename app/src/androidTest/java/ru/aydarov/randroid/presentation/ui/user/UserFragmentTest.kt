package ru.aydarov.randroid.presentation.ui.user

import org.junit.Assert
import org.junit.Test

/**
 * @author Aydarov Askhar 2020
 */
class UserFragmentTest {

    private val fragment = UserFragment()
    private var SingleActivity = ru.aydarov.randroid.presentation.common.SingleActivity()
    @Test
    fun testTokensHas() {
        SingleActivity.nullifyTokens()
        Assert.assertEquals(true, fragment.hasTokens())
    }
}