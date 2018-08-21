package com.jahnelgroup

import org.springframework.stereotype.Component

@Component // attempting to register
class LonelyClass

// This class wants to be a Bean but it's not in the right package.

// It needs to be located within or under the package where the IoC container starts.