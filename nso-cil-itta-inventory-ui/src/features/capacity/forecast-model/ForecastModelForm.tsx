import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { FORECAST_MODEL_ALGORITHM_OPTIONS, FORECAST_MODEL_CRITICALITY_OPTIONS, ForecastModelAlgorithm, ForecastModelCriticality } from './types';
import { validateForecastModel, cleanForecastModelValues } from './schema';
import { FORECASTMODEL_DEFAULTS } from './types';
import type { ForecastModelFormValues, ForecastModelInput } from './types';

interface Props {
  initial?: ForecastModelFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ForecastModelInput) => void;
  onCancel?: () => void;
}

export function ForecastModelForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ForecastModelFormValues>({ ...FORECASTMODEL_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ForecastModelFormValues>(key: K, value: ForecastModelFormValues[K]) {
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
    const result = validateForecastModel(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanForecastModelValues(values as Record<string, unknown>) as unknown as ForecastModelInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Forecast Model form">
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
      <FormField label="Model Name" htmlFor="modelName" error={errors.modelName} required={true}>
        <Input
          id="modelName"
          value={values.modelName ?? ''}
          maxLength={255}
          onChange={(v) => set('modelName', v)}
        />
      </FormField>
      <FormField label="Algorithm" htmlFor="algorithm" error={errors.algorithm} required={false}>
        <Select
          id="algorithm"
          value={values.algorithm ?? ''}
          options={FORECAST_MODEL_ALGORITHM_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('algorithm', (v || undefined) as ForecastModelAlgorithm | undefined)}
        />
      </FormField>
      <FormField label="Trained On" htmlFor="trainedOn" error={errors.trainedOn} required={false}>
        <DateTimeInput
          id="trainedOn"
          value={values.trainedOn ?? ''}
          onChange={(v) => set('trainedOn', v)}
        />
      </FormField>
      <FormField label="Mape" htmlFor="mape" error={errors.mape} required={false}>
        <NumberInput
          id="mape"
          value={values.mape}
          step={0.0001}
          onChange={(v) => set('mape', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={FORECAST_MODEL_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ForecastModelCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
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
