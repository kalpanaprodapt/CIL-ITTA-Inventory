import { expect, test } from '@playwright/test';

test.describe('Discovery and Reconciliation', () => {
  test('Discovery Job list renders', async ({ page }) => {
    await page.goto('/discovery/discovery-jobs');
    await expect(page.getByRole('heading', { name: 'Discovery Jobs' }).or(page.getByRole('heading', { name: 'Discovery Jobes' }))).toBeVisible();
  });

  test('Discovery Job create form is reachable', async ({ page }) => {
    await page.goto('/discovery/discovery-jobs/new');
    await expect(page.getByRole('form', { name: 'Discovery Job form' })).toBeVisible();
  });

  test('Discovery Profile list renders', async ({ page }) => {
    await page.goto('/discovery/discovery-profiles');
    await expect(page.getByRole('heading', { name: 'Discovery Profiles' }).or(page.getByRole('heading', { name: 'Discovery Profilees' }))).toBeVisible();
  });

  test('Discovery Profile create form is reachable', async ({ page }) => {
    await page.goto('/discovery/discovery-profiles/new');
    await expect(page.getByRole('form', { name: 'Discovery Profile form' })).toBeVisible();
  });

  test('Discovered Asset list renders', async ({ page }) => {
    await page.goto('/discovery/discovered-assets');
    await expect(page.getByRole('heading', { name: 'Discovered Assets' }).or(page.getByRole('heading', { name: 'Discovered Assetes' }))).toBeVisible();
  });

  test('Discovered Asset create form is reachable', async ({ page }) => {
    await page.goto('/discovery/discovered-assets/new');
    await expect(page.getByRole('form', { name: 'Discovered Asset form' })).toBeVisible();
  });

  test('Reconciliation Rule list renders', async ({ page }) => {
    await page.goto('/discovery/reconciliation-rules');
    await expect(page.getByRole('heading', { name: 'Reconciliation Rules' }).or(page.getByRole('heading', { name: 'Reconciliation Rulees' }))).toBeVisible();
  });

  test('Reconciliation Rule create form is reachable', async ({ page }) => {
    await page.goto('/discovery/reconciliation-rules/new');
    await expect(page.getByRole('form', { name: 'Reconciliation Rule form' })).toBeVisible();
  });

  test('Reconciliation Result list renders', async ({ page }) => {
    await page.goto('/discovery/reconciliation-results');
    await expect(page.getByRole('heading', { name: 'Reconciliation Results' }).or(page.getByRole('heading', { name: 'Reconciliation Resultes' }))).toBeVisible();
  });

  test('Reconciliation Result create form is reachable', async ({ page }) => {
    await page.goto('/discovery/reconciliation-results/new');
    await expect(page.getByRole('form', { name: 'Reconciliation Result form' })).toBeVisible();
  });
});
