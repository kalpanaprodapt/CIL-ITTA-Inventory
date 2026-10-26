import { expect, test } from '@playwright/test';

test.describe('Circuits', () => {
  test('Circuit list renders', async ({ page }) => {
    await page.goto('/circuit/circuits');
    await expect(page.getByRole('heading', { name: 'Circuits' }).or(page.getByRole('heading', { name: 'Circuites' }))).toBeVisible();
  });

  test('Circuit create form is reachable', async ({ page }) => {
    await page.goto('/circuit/circuits/new');
    await expect(page.getByRole('form', { name: 'Circuit form' })).toBeVisible();
  });

  test('Circuit Segment list renders', async ({ page }) => {
    await page.goto('/circuit/circuit-segments');
    await expect(page.getByRole('heading', { name: 'Circuit Segments' }).or(page.getByRole('heading', { name: 'Circuit Segmentes' }))).toBeVisible();
  });

  test('Circuit Segment create form is reachable', async ({ page }) => {
    await page.goto('/circuit/circuit-segments/new');
    await expect(page.getByRole('form', { name: 'Circuit Segment form' })).toBeVisible();
  });

  test('Circuit Provider list renders', async ({ page }) => {
    await page.goto('/circuit/circuit-providers');
    await expect(page.getByRole('heading', { name: 'Circuit Providers' }).or(page.getByRole('heading', { name: 'Circuit Provideres' }))).toBeVisible();
  });

  test('Circuit Provider create form is reachable', async ({ page }) => {
    await page.goto('/circuit/circuit-providers/new');
    await expect(page.getByRole('form', { name: 'Circuit Provider form' })).toBeVisible();
  });

  test('Circuit Termination list renders', async ({ page }) => {
    await page.goto('/circuit/circuit-terminations');
    await expect(page.getByRole('heading', { name: 'Circuit Terminations' }).or(page.getByRole('heading', { name: 'Circuit Terminationes' }))).toBeVisible();
  });

  test('Circuit Termination create form is reachable', async ({ page }) => {
    await page.goto('/circuit/circuit-terminations/new');
    await expect(page.getByRole('form', { name: 'Circuit Termination form' })).toBeVisible();
  });

  test('Circuit Order list renders', async ({ page }) => {
    await page.goto('/circuit/circuit-orders');
    await expect(page.getByRole('heading', { name: 'Circuit Orders' }).or(page.getByRole('heading', { name: 'Circuit Orderes' }))).toBeVisible();
  });

  test('Circuit Order create form is reachable', async ({ page }) => {
    await page.goto('/circuit/circuit-orders/new');
    await expect(page.getByRole('form', { name: 'Circuit Order form' })).toBeVisible();
  });
});
