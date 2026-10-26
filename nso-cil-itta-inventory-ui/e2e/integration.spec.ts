import { expect, test } from '@playwright/test';

test.describe('Integration Layer', () => {
  test('Integration Endpoint list renders', async ({ page }) => {
    await page.goto('/integration/integration-endpoints');
    await expect(page.getByRole('heading', { name: 'Integration Endpoints' }).or(page.getByRole('heading', { name: 'Integration Endpointes' }))).toBeVisible();
  });

  test('Integration Endpoint create form is reachable', async ({ page }) => {
    await page.goto('/integration/integration-endpoints/new');
    await expect(page.getByRole('form', { name: 'Integration Endpoint form' })).toBeVisible();
  });

  test('Sync Job list renders', async ({ page }) => {
    await page.goto('/integration/sync-jobs');
    await expect(page.getByRole('heading', { name: 'Sync Jobs' }).or(page.getByRole('heading', { name: 'Sync Jobes' }))).toBeVisible();
  });

  test('Sync Job create form is reachable', async ({ page }) => {
    await page.goto('/integration/sync-jobs/new');
    await expect(page.getByRole('form', { name: 'Sync Job form' })).toBeVisible();
  });

  test('Sync Mapping list renders', async ({ page }) => {
    await page.goto('/integration/sync-mappings');
    await expect(page.getByRole('heading', { name: 'Sync Mappings' }).or(page.getByRole('heading', { name: 'Sync Mappinges' }))).toBeVisible();
  });

  test('Sync Mapping create form is reachable', async ({ page }) => {
    await page.goto('/integration/sync-mappings/new');
    await expect(page.getByRole('form', { name: 'Sync Mapping form' })).toBeVisible();
  });

  test('Import Batch list renders', async ({ page }) => {
    await page.goto('/integration/import-batchs');
    await expect(page.getByRole('heading', { name: 'Import Batchs' }).or(page.getByRole('heading', { name: 'Import Batches' }))).toBeVisible();
  });

  test('Import Batch create form is reachable', async ({ page }) => {
    await page.goto('/integration/import-batchs/new');
    await expect(page.getByRole('form', { name: 'Import Batch form' })).toBeVisible();
  });

  test('Webhook Subscription list renders', async ({ page }) => {
    await page.goto('/integration/webhook-subscriptions');
    await expect(page.getByRole('heading', { name: 'Webhook Subscriptions' }).or(page.getByRole('heading', { name: 'Webhook Subscriptiones' }))).toBeVisible();
  });

  test('Webhook Subscription create form is reachable', async ({ page }) => {
    await page.goto('/integration/webhook-subscriptions/new');
    await expect(page.getByRole('form', { name: 'Webhook Subscription form' })).toBeVisible();
  });
});
