import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { DEPRECIATION_SCHEDULE_METHOD_OPTIONS, DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_OPTIONS, DepreciationScheduleMethod, DepreciationScheduleLifecycleStage } from './types';
import { validateDepreciationSchedule, cleanDepreciationScheduleValues } from './schema';
import { DEPRECIATIONSCHEDULE_DEFAULTS } from './types';
import type { DepreciationScheduleFormValues, DepreciationScheduleInput } from './types';

interface Props {
  initial?: DepreciationScheduleFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: DepreciationScheduleInput) => void;
  onCancel?: () => void;
}

export function DepreciationScheduleForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<DepreciationScheduleFormValues>({ ...DEPRECIATIONSCHEDULE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof DepreciationScheduleFormValues>(key: K, value: DepreciationScheduleFormValues[K]) {
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
    const result = validateDepreciationSchedule(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanDepreciationScheduleValues(values as Record<string, unknown>) as unknown as DepreciationScheduleInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Depreciation Schedule form">
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
      <FormField label="Asset Id" htmlFor="assetId" error={errors.assetId} required={true}>
        <Input
          id="assetId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.assetId ?? ''}
          onChange={(v) => set('assetId', v)}
        />
      </FormField>
      <FormField label="Method" htmlFor="method" error={errors.method} required={false}>
        <Select
          id="method"
          value={values.method ?? ''}
          options={DEPRECIATION_SCHEDULE_METHOD_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('method', (v || undefined) as DepreciationScheduleMethod | undefined)}
        />
      </FormField>
      <FormField label="Useful Life Months" htmlFor="usefulLifeMonths" error={errors.usefulLifeMonths} required={true}>
        <NumberInput
          id="usefulLifeMonths"
          value={values.usefulLifeMonths}
          step={1}
          onChange={(v) => set('usefulLifeMonths', v)}
        />
      </FormField>
      <FormField label="Salvage Value" htmlFor="salvageValue" error={errors.salvageValue} required={false}>
        <NumberInput
          id="salvageValue"
          value={values.salvageValue}
          step={0.0001}
          onChange={(v) => set('salvageValue', v)}
        />
      </FormField>
      <FormField label="Start Date" htmlFor="startDate" error={errors.startDate} required={false}>
        <DateInput
          id="startDate"
          value={values.startDate ?? ''}
          onChange={(v) => set('startDate', v)}
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
          options={DEPRECIATION_SCHEDULE_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as DepreciationScheduleLifecycleStage | undefined)}
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
