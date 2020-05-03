	.data
	.align 2
_a:	.space 4	# Global var a
	.text
	.globl main
main:	
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 12		#set the FP for this function
	subu  $sp, $sp, 12		#push space for locals
_main_Exit:
	lw    $ra, -4($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -8($fp)	#restore FP
	move  $sp, $t0		#restore SP
	li    $v0, $t0		#load exit code for syscall
	syscall		#only do this for main
	.text
_f:	
	sw    $ra, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	sw    $fp, 0($sp)	#PUSH
	subu  $sp, $sp, 4
	addu  $fp, $sp, 8		#set the FP for this function
	subu  $sp, $sp, 0		#push space for locals
_f_Exit:
	lw    $ra, 0($fp)
	move  $t0, $fp		#save control link
	lw    $fp, -4($fp)	#restore FP
	move  $sp, $t0		#restore SP
	jr    $ra
