import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { IMPORT_BATCH_BATCH_STATE_OPTIONS, IMPORT_BATCH_LIFECYCLE_STAGE_OPTIONS, ImportBatchBatchState, ImportBatchLifecycleStage } from './types';
import { validateImportBatch, cleanImportBatchValues } from './schema';
import { IMPORTBATCH_DEFAULTS } from './types';
import type { ImportBatchFormValues, ImportBatchInput } from './types';

interface Props {
  initial?: ImportBatchFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ImportBatchInput) => void;
  onCancel?: () => void;
}

export function ImportBatchForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ImportBatchFormValues>({ ...IMPORTBATCH_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ImportBatchFormValues>(key: K, value: ImportBatchFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateImportBatch(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanImportBatchValues(values as Record<string, unknown>) as unknown as ImportBatchInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Import Batch form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="File Name" htmlFor="fileName" error={errors.fileName} required={true}>
        <Input
          id="fileName"
          value={values.fileName ?? ''}
          maxLength={255}
          onChange={(v) => set('fileName', v)}
        />
      </FormField>
      <FormField label="Entity Type" htmlFor="entityType" error={errors.entityType} required={true}>
        <Input
          id="entityType"
          value={values.entityType ?? ''}
          maxLength={255}
          onChange={(v) => set('entityType', v)}
        />
      </FormField>
      <FormField label="Total Rows" htmlFor="totalRows" error={errors.totalRows} required={false}>
        <NumberInput
          id="totalRows"
          value={values.totalRows}
          step={1}
          onChange={(v) => set('totalRows', v)}
        />
      </FormField>
      <FormField label="Processed Rows" htmlFor="processedRows" error={errors.processedRows} required={false}>
        <NumberInput
          id="processedRows"
          value={values.processedRows}
          step={1}
          onChange={(v) => set('processedRows', v)}
        />
      </FormField>
      <FormField label="Failed Rows" htmlFor="failedRows" error={errors.failedRows} required={false}>
        <NumberInput
          id="failedRows"
          value={values.failedRows}
          step={1}
          onChange={(v) => set('failedRows', v)}
        />
      </FormField>
      <FormField label="Batch State" htmlFor="batchState" error={errors.batchState} required={false}>
        <Select
          id="batchState"
          value={values.batchState ?? ''}
          options={IMPORT_BATCH_BATCH_STATE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('batchState', (v || undefined) as ImportBatchBatchState | undefined)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={IMPORT_BATCH_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ImportBatchLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
