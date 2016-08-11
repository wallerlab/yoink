/**
 *
 *  Adaptive calculators for performing smooothening in the buffer region as
 *  molecules go in and out.
 *
 *
 *  It is a four level affair:
 *
 *    -Processors
 *      -Weight factors (only needed for multi-configurations)
 *       - smooth factors
 *        - smoothening functions
 *
 * @author Min Zheng
 */
package org.wallerlab.yoink.adaptive.services;